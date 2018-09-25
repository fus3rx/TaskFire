package imaginers.com.taskfire.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import imaginers.com.taskfire.Adapter.WorkspaceList;
import imaginers.com.taskfire.Model.Workspace;
import imaginers.com.taskfire.R;

public class MainActivity extends AppCompatActivity {

    public static final String WORKSPACE_ID = "com.imaginers.com.firetask.workspacenameid";
    public static final String WORKSPACE_NAME = "com.imaginers.com.firetask.workspacename";
    //public static final String WORKSPACE_TYPE = "com.imaginers.com.firetask.workspacenametype";

    EditText editTextWorkspaceName;
    Spinner spinnerWorkspace;
    Button buttonShowDialog, buttonAddWorkspace;
    ListView ListViewWorkspace;

    //a list to store all the artist from firebase database
    List<Workspace> workspaces;

    //our database reference object
    DatabaseReference databaseWorkspaces;

    FirebaseAuth mAuth;

    String mCurrentUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //getting the reference of artists node
        databaseWorkspaces = database.getInstance().getReference("workspaces");
        mAuth = FirebaseAuth.getInstance();
        mCurrentUid = mAuth.getUid();

        //getting views

        ListViewWorkspace = findViewById(R.id.listviewWorkSpace);
        buttonShowDialog = findViewById(R.id.button);

        //list to store artists
        workspaces = new ArrayList<>();


        //adding an onclicklistener to button
        buttonShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling the method addArtist()
                //the method is defined below
                //this method is actually performing the write operation
                addWorkspace();
            }
        });

        //attaching listener to listview
        ListViewWorkspace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the selected artist
                Workspace workspace = workspaces.get(i);

                //creating an intent
                Intent intent = new Intent(getApplicationContext(), WorkspaceActivity.class);

                //putting artist name and id to intent
                intent.putExtra(WORKSPACE_ID, workspace.getWsID());
                intent.putExtra(WORKSPACE_NAME, workspace.getWsName());

                //starting the activity with intent
                startActivity(intent);
            }
        });

        ListViewWorkspace.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Workspace workspace = workspaces.get(i);
                showUpdateDeleteDialog(workspace.getWsID(), workspace.getWsName());
                return true;
            }
        });
    }

    private void showUpdateDeleteDialog(final String workspaceId, String workspaceName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final Spinner spinnerGenre = (Spinner) dialogView.findViewById(R.id.spinnerGenres);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateArtist);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteArtist);

        dialogBuilder.setTitle(workspaceName);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String workspaceName = editTextName.getText().toString().trim();
                String workspaceType = spinnerGenre.getSelectedItem().toString();
                if (!TextUtils.isEmpty(workspaceName)) {
                    updateWorkspace(workspaceId, workspaceName, workspaceType);
                    b.dismiss();
                }
            }
        });


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String workspaceId = databaseWorkspaces.push().getKey();
                deleteWorkspace(workspaceId);
                b.dismiss();
            }
        });
    }

    private void addWorkspace() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.wslayout, null);
        dialogBuilder.setView(dialogView);

        editTextWorkspaceName = dialogView.findViewById(R.id.editTextWorkspaceName);
        spinnerWorkspace = dialogView.findViewById(R.id.spinnerWorkspaceType);
        buttonAddWorkspace = dialogView.findViewById(R.id.buttonAddWorkspace);

        dialogBuilder.setTitle("workspaceName");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonAddWorkspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //getting the values to save
                String workspaceName = editTextWorkspaceName.getText().toString().trim();
                String workspaceType = spinnerWorkspace.getSelectedItem().toString();

                //checking if the value is provided
                if (!TextUtils.isEmpty(workspaceName)) {

                    //getting a unique id using push().getKey() method
                    //it will create a unique id and we will use it as the Primary Key for our Artist
                    String workspaceId = databaseWorkspaces.push().getKey();

                    //creating an Artist Object
                    Workspace workspace = new Workspace(workspaceId, workspaceName, workspaceType, mCurrentUid);

                    //Saving the Artist
                    databaseWorkspaces.child(workspaceId).setValue(workspace);

                    //setting edittext to blank again
                    editTextWorkspaceName.setText("");

                    //displaying a success toast
                    Toast.makeText(MainActivity.this, "Workspace added", Toast.LENGTH_LONG).show();
                } else {
                    //if the value is not given displaying a toast
                    Toast.makeText(MainActivity.this, "Please enter a name", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private boolean updateWorkspace(String workspaceId, String workspaceName, String workspaceType) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("workspace").child(workspaceId);

        //updating artist
        Workspace workspace = new Workspace(workspaceId, workspaceName, workspaceType, mCurrentUid);
        dR.setValue(workspace);
        Toast.makeText(getApplicationContext(), "Workspace Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deleteWorkspace(String id) {
        //getting the specified workspaces reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("workspaces").child(id);

        //removing artist
        dR.removeValue();

        //getting the projects reference for the specified artist
        DatabaseReference drTracks = FirebaseDatabase.getInstance().getReference("projects").child(id);

        //removing all projects
        drTracks.removeValue();
        Toast.makeText(getApplicationContext(), "Projects Deleted", Toast.LENGTH_LONG).show();

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseApp.initializeApp(this);
        //attaching value event listener
        databaseWorkspaces.orderByChild("uId").equalTo(mCurrentUid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //clearing the previous workspaces list
                        workspaces.clear();

                        //iterating through all the nodes
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            //getting workspaces
                            Workspace workspace = postSnapshot.getValue(Workspace.class);
                            //adding workspaces to the list
                            workspaces.add(workspace);
                        }

                        //creating adapter
                        WorkspaceList workspaceAdapter = new WorkspaceList(MainActivity.this, workspaces);
                        //attaching adapter to the listview
                        ListViewWorkspace.setAdapter(workspaceAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
