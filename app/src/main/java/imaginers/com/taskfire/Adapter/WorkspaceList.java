package imaginers.com.taskfire.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import imaginers.com.taskfire.Model.Workspace;
import imaginers.com.taskfire.R;


public class WorkspaceList extends ArrayAdapter<Workspace> {
    private Activity context;
    List<Workspace> workspaces;

    public WorkspaceList(Activity context, List<Workspace> workspaces) {
        super(context, R.layout.layout_workspace_list, workspaces);
        this.context = context;
        this.workspaces = workspaces;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_workspace_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewType = (TextView) listViewItem.findViewById(R.id.textViewGenre);

        Workspace workspace = workspaces.get(position);
        textViewName.setText(workspace.getWsName());
        textViewType.setText(workspace.getWsType());

        return listViewItem;
    }
}