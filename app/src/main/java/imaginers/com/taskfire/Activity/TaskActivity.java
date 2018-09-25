package imaginers.com.taskfire.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import imaginers.com.taskfire.Model.Project;
import imaginers.com.taskfire.Model.Task;
import imaginers.com.taskfire.Model.Workspace;
import imaginers.com.taskfire.R;

public class TaskActivity extends AppCompatActivity {

    Button buttonAddTask, buttonShowTask;
    EditText editTask, editTaskSubject, editTaskDeadline;
    TextView textViewProject;
    ListView listViewTask;

    DatabaseReference databaseProjects;

    List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Intent intent = getIntent();

        /*
         * this line is important
         * this time we are not getting the reference of a direct node
         * but inside the node track we are creating a new child with the artist id
         * and inside that node we will store all the projects with unique ids
         * */
//        databaseProjects = FirebaseDatabase.getInstance().getReference("tasks").child(intent.getStringExtra(MainActivity.WORKSPACE_ID));

//        listViewProject = findViewById(R.id.listViewProjects);
//        buttonShowProject = findViewById(R.id.buttonShowProject);
//        textViewWorkspace = findViewById(R.id.textViewWorkspace);
//
//
//
//
//        projects = new ArrayList<>();
//
//        textViewWorkspace.setText(intent.getStringExtra(MainActivity.WORKSPACE_NAME));
//
//
//        //adding an onclicklistener to button
//        buttonShowDialog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //calling the method addArtist()
//                //the method is defined below
//                //this method is actually performing the write operation
//                addWorkspace();
//            }
//        });
//
//        //attaching listener to listview
//        ListViewWorkspace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                //getting the selected artist
//                Workspace workspace = workspaces.get(i);
//
//                //creating an intent
//                Intent intent = new Intent(getApplicationContext(), WorkspaceActivity.class);
//
//                //putting artist name and id to intent
//                intent.putExtra(WORKSPACE_ID, workspace.getWsID());
//                intent.putExtra(WORKSPACE_NAME, workspace.getWsName());
//
//                //starting the activity with intent
//                startActivity(intent);
//            }
//        });
//
//        ListViewWorkspace.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Workspace workspace = workspaces.get(i);
//                showUpdateDeleteDialog(workspace.getWsID(), workspace.getWsName());
//                return true;
//            }
//        });
    }
}
