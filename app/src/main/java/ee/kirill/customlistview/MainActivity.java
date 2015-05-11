package ee.kirill.customlistview;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ListActivity {

    private State[] states;
    private String[] countries;
    private String[] capitals;
    private String[] flags;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        countries = getResources().getStringArray(R.array.countries);
        capitals = getResources().getStringArray(R.array.capitals);
        flags = getResources().getStringArray(R.array.flags);
        states = new State[countries.length];
        for (int i = 0; i < countries.length; i++) {
            int id = getResources().getIdentifier(flags[i], "drawable", getPackageName());
            states[i] = new State(countries[i], capitals[i], id);
        }

        setListAdapter(new StateAdapter(states));
        final MainActivity main = this;
        AdapterView.OnItemLongClickListener itemLongListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(main, InfoActivity.class);

                intent.putExtra("country", countries[position]);
                intent.putExtra("capital", capitals[position]);

                int imgId = getResources().getIdentifier(flags[position], "drawable", getPackageName());
                intent.putExtra("imageId", imgId);

                startActivity(intent);
                return false;
            }
        };
        getListView().setOnItemLongClickListener(itemLongListener);

        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // получаем выбранный пункт
                State selectedState = (State) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Был выбран пункт " + selectedState.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        };
        getListView().setOnItemClickListener(itemListener);
    }

    private State getModel(int position) {
        return (((StateAdapter) getListAdapter()).getItem(position));
    }

    class StateAdapter extends ArrayAdapter<State> {

        private LayoutInflater mInflater;

        StateAdapter(State[] list) {
            super(MainActivity.this, R.layout.activity_main, list);
            mInflater = LayoutInflater.from(MainActivity.this);
        }

        public View getView(int position, View convertView,
                            ViewGroup parent) {
            ViewHolder holder;
            View row = convertView;
            if (row == null) {

                row = mInflater.inflate(R.layout.activity_main, parent, false);
                holder = new ViewHolder();
                holder.imageView = (ImageView) row.findViewById(R.id.flag);
                holder.nameView = (TextView) row.findViewById(R.id.name);
                holder.capitalView = (TextView) row.findViewById(R.id.county);
                row.setTag(holder);
            } else {

                holder = (ViewHolder) row.getTag();
            }

            State state = getModel(position);

            holder.imageView.setImageResource((state.getFlagResource()));
            holder.nameView.setText(state.getName());
            holder.capitalView.setText(state.getCapital());

            return row;
        }

        class ViewHolder {
            public ImageView imageView;
            public TextView nameView, capitalView;
        }
    }
}
