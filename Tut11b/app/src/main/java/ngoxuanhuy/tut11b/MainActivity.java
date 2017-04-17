package ngoxuanhuy.tut11b;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Product> model = new ArrayList<Product>();
    ArrayAdapter<Product> adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get reference to ListView from activity_main layout
        ListView product_list = (ListView) findViewById(R.id.product_list);
        adapter = new ArrayAdapter<Product>(MainActivity.this,
                                            android.R.layout.simple_list_item_1,
                                            model);
        product_list.setAdapter(adapter);
        product_list.setOnItemClickListener(OnItemClick);
    }

    private AdapterView.OnItemClickListener OnItemClick=new
            AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent,
                                        View view, final int position,
                                        long id) {
                    final Product p = model.get(position);
                    LayoutInflater inflater = getLayoutInflater();
                    final View inflator = inflater.inflate(R.layout.dialog_change, null);

                    //LayoutInflater linf = LayoutInflater.from(this);
                    //final View inflator = linf.inflate(R.layout.dialog_insert, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    final EditText etName =  (EditText) inflator.findViewById(R.id.name);
                    final EditText etQuantity = (EditText) inflator.findViewById(R.id.quantity);
                    final EditText etPrice = (EditText) inflator.findViewById(R.id.price);

                    etName.setText(p.getName());
                    etQuantity.setText(p.getQuantity());
                    etPrice.setText(p.getPrice());

                    // Inflate and set the layout for the dialog
                    // Pass null as the parent view because its going in the dialog layout
                    builder.setView(inflator)
                            .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    adapter.remove(p);
                                    p.setName(etName.getText().toString());
                                    p.setQuantity(etQuantity.getText().toString());
                                    p.setPrice(etPrice.getText().toString());
                                    adapter.insert(p,position);
                                }
                            }).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    adapter.remove(p);
                                    //model.remove(position);
                                }
                            });
                    builder.create();
                    builder.show();
                }
            };

    // Create option menu with 2 options: "insert" and "show_total_amount"
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.option, menu);
        return(super.onCreateOptionsMenu(menu));
    }

    // when an option item is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.dialog_box) {
            // Get the layout inflater
            LayoutInflater inflater = this.getLayoutInflater();
            final View inflator = inflater.inflate(R.layout.dialog_insert, null);

            //LayoutInflater linf = LayoutInflater.from(this);
            //final View inflator = linf.inflate(R.layout.dialog_insert, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            final EditText etName =  (EditText) inflator.findViewById(R.id.name);
            final EditText etQuantity = (EditText) inflator.findViewById(R.id.quantity);
            final EditText etPrice = (EditText) inflator.findViewById(R.id.price);

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflator)
                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            Product r = new Product();
                            String stName = etName.getText().toString();
                            String stQuantity = etQuantity.getText().toString();
                            String stPrice = etPrice.getText().toString();
                            r.setName(stName);
                            r.setPrice(stPrice);
                            r.setQuantity(stQuantity);
                            adapter.add(r);

                        }
                    });
            builder.create();
            builder.show();


            return(true);
        }
        else if (item.getItemId()==R.id.show_total){
            // Show dialog box with number of products in the list
            // Get the layout inflater
            LayoutInflater inflater = this.getLayoutInflater();
            final View inflator = inflater.inflate(R.layout.show_total_amount, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            final TextView numOfProducts =  (TextView) inflator.findViewById(R.id.numOfProducts);
            final TextView tvQuantity = (TextView) inflator.findViewById(R.id.quantity);
            final TextView tvPrice = (TextView) inflator.findViewById(R.id.price);

            numOfProducts.setText("Number of products: " +   String.valueOf(model.size()));

            int quantity = 0;
            int price = 0;

            for (int i=0; i < model.size(); i++){
                Product p = model.get(i);
                quantity += Integer.parseInt(p.getQuantity().toString());
                price += Integer.parseInt(p.getPrice().toString())*Integer.parseInt(p.getQuantity().toString());
            }

            tvQuantity.setText("Quantity: " + String.valueOf(quantity));
            tvPrice.setText("Price: " + String.valueOf(price));


            builder.setView(inflator).create().show();

            return (true);
        }
        return(super.onOptionsItemSelected(item));
    }
}
