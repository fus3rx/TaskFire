package imaginers.com.taskfire.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import imaginers.com.taskfire.Model.Project;
import imaginers.com.taskfire.R;


public class ProjectList extends ArrayAdapter<Project> {
    private Activity context;
    List<Project> projects;

    public ProjectList(Activity context, List<Project> projects) {
        super(context, R.layout.layout_workspace_list, projects);
        this.context = context;
        this.projects = projects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_workspace_list, null, true);

        TextView textViewName = listViewItem.findViewById(R.id.textViewName);
        TextView textViewType = listViewItem.findViewById(R.id.textViewGenre);

        Project project = projects.get(position);
        textViewName.setText(project.getProjectName());
        textViewType.setText(project.getProjectType());

        return listViewItem;
    }
}