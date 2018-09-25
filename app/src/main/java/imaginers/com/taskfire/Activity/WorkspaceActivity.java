package imaginers.com.taskfire.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import imaginers.com.taskfire.Adapter.ProjectList;
import imaginers.com.taskfire.Model.Project;
import imaginers.com.taskfire.R;

public class WorkspaceActivity extends AppCompatActivity {

    Button buttonAddProject, buttonShowProject;
    EditText editProject;
    TextView textViewWorkspace;
    ListView listViewProject;
    Spinner spinnerProject;

    DatabaseReference databaseProjects;

    List<Project> projects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workspace);

        Intent intent = getIntent();

        /*
         * this line is important
         * this time we are not getting the reference of a direct node
         * but inside the node track we are creating a new child with the artist id
         * and inside that node we will store all the projects with unique ids
         * */
        databaseProjects = FirebaseDatabase.getInstance().getReference("projects").child(intent.getStringExtra(MainActivity.WORKSPACE_ID));

        listViewProject = findViewById(R.id.listViewProjects);
        buttonShowProject = findViewById(R.id.buttonShowProject);
        textViewWorkspace = findViewById(R.id.textViewWorkspace);


        projects = new ArrayList<>();

        textViewWorkspace.setText(intent.getStringExtra(MainActivity.WORKSPACE_NAME));

        buttonShowProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(WorkspaceActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.prolayout, null);
                dialogBuilder.setView(dialogView);
                buttonAddProject = dialogView.findViewById(R.id.buttonAddProject);
                spinnerProject = dialogView.findViewById(R.id.spinnerProject);
                editProject = dialogView.findViewById(R.id.editTextProject);
                dialogBuilder.setTitle("project Name");
                final AlertDialog b = dialogBuilder.create();
                b.show();
                buttonAddProject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveProject();
                    }
                });

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseProjects.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                projects.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Project project = postSnapshot.getValue(Project.class);
                    projects.add(project);
                }
                ProjectList projectListAdapter = new ProjectList(WorkspaceActivity.this, projects);
                listViewProject.setAdapter(projectListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void saveProject() {
        String projectName = editProject.getText().toString().trim();
        String projectType = spinnerProject.getSelectedItem().toString();

        if (!TextUtils.isEmpty(projectName)) {
            String projectID = databaseProjects.push().getKey();
            Project project = new Project(projectID, projectName, projectType);
            databaseProjects.child(projectID).setValue(project);
            Toast.makeText(this, "Project saved", Toast.LENGTH_LONG).show();
            editProject.setText("");
        } else {
            Toast.makeText(this, "Please enter Project name", Toast.LENGTH_LONG).show();
        }
    }
}
