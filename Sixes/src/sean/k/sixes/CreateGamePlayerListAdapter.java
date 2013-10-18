package sean.k.sixes;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CreateGamePlayerListAdapter extends ArrayAdapter<Player> {


	List<Player> list;
	SixesOfflineCreate c;

	public CreateGamePlayerListAdapter(SixesOfflineCreate context, int textViewResourceId, ArrayList<Player> playerList) {
		super(context, textViewResourceId, playerList);
		c = context;
		list = playerList;


	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// return super.getView(position, convertView, parent);
		
		LayoutInflater inflater = (LayoutInflater) c.getLayoutInflater();
		//LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row = inflater.inflate(R.layout.player_panel_create_entry, parent, false);
		
		ImageView dead = (ImageView) row.findViewById(R.id.player_panel_create_check);
		TextView name = (TextView) row.findViewById(R.id.player_panel_create_name);
		TextView showdownScore = (TextView) row.findViewById(R.id.player_panel_create_score);
		
		
		dead.setVisibility(ImageView.VISIBLE);
		showdownScore.setVisibility(TextView.INVISIBLE);
		name.setText(list.get(position).getName());
		name.setTextColor(Color.WHITE);
		

		return row;
	}

}
