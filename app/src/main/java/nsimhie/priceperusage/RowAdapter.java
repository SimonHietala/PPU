package nsimhie.priceperusage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nsimhie on 2015-10-06.
 */
public class RowAdapter extends BaseAdapter
{
    private final Context context;
    private ArrayList<Usage> usages;

    public RowAdapter(ArrayList<Usage> usages, Context context)
    {
        this.usages = usages;
        this.context = context;
    }

    @Override
    public int getCount() {
        return usages.size();
    }

    @Override
    public Object getItem(int pos) {
        return usages.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
        //just return 0 if your list items do not have an Id variable.
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row, null);
        }

        TextView name = (TextView)view.findViewById(R.id.name);
        TextView price = (TextView)view.findViewById(R.id.price);
        TextView uses = (TextView)view.findViewById(R.id.uses);
        TextView PPU = (TextView)view.findViewById(R.id.PPU);

        name.setText(usages.get(position).getName());
        price.setText(Double.toString(usages.get(position).getPrice()));
        uses.setText(Integer.toString(usages.get(position).getUses()));
        PPU.setText(usages.get(position).getPPU());

        Button minusBtn = (Button)view.findViewById(R.id.minusBtn);
        Button plusBtn = (Button)view.findViewById(R.id.plusBtn);
        Button delBtn = (Button) view.findViewById(R.id.delBtn);

        minusBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                usages.get(position).setUses(usages.get(position).getUses() - 1); //or some other task
                notifyDataSetChanged();
            }
        });
        plusBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                usages.get(position).setUses(usages.get(position).getUses() + 1);
                notifyDataSetChanged();
            }
        });

        delBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                new AlertDialog.Builder(context)
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                // continue with delete
                                usages.remove(position);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });

        return view;
    }
}
