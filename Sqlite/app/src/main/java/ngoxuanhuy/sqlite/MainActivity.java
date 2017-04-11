package ngoxuanhuy.sqlite;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Cursor model=null;
    EditText name=null;
    EditText address=null;
    EditText notes=null;
    RadioGroup types=null;
    Product helper=null;
    ProductAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper=new Product(this);

        ListView list=(ListView)findViewById(R.id.product_list);

        model=helper.getAll();
        startManagingCursor(model);
        adapter=new ProductAdapter(model);
        list.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        helper.close();
    }

    static class ProductHolder {
        private TextView name = null;
        private TextView quantity = null;
        private TextView price = null;

        ProductHolder(View row) {
            name = (TextView) row.findViewById(R.id.name);
            quantity = (TextView) row.findViewById(R.id.quantity);
            price = (TextView) row.findViewById(R.id.price);
        }

        void populateFrom(Cursor c, Product helper) {
            name.setText(helper.getName(c));
            quantity.setText(helper.getQuantity(c));
            price.setText(helper.getPrice(c));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.option, menu);
        return(super.onCreateOptionsMenu(menu));
    }

    final String mput = "";

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

                            String stName = etName.getText().toString();
                            String stQuantity = etQuantity.getText().toString();
                            String stPrice = etPrice.getText().toString();
                            helper.insert(stName, stQuantity, stPrice);
                            model.requery();

                        }
                    });
            builder.create();
            builder.show();


            return(true);
        }
        return(super.onOptionsItemSelected(item));
    }


    class ProductAdapter extends CursorAdapter {
        ProductAdapter(Cursor c) {
            super(MainActivity.this, c);
        }

        @Override
        public void bindView(View row, Context ctxt,
                             Cursor c) {
            ProductHolder holder=(ProductHolder)row.getTag();

            holder.populateFrom(c, helper);
        }

        @Override
        public View newView(Context ctxt, Cursor c,
                            ViewGroup parent) {
            LayoutInflater inflater=getLayoutInflater();
            View row=inflater.inflate(R.layout.row, parent, false);
            ProductHolder holder=new ProductHolder(row);

            row.setTag(holder);

            return(row);
        }
    }
}
