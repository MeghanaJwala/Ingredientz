package com.example.ingredientz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.job.JobInfo;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.ui.auth.data.model.User;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class HomePage extends AppCompatActivity {

    Spinner category_buy, subcategory_buy;
    ArrayList<String> a, b, c, d, e, f, g, h, i, j, k;
    ArrayAdapter<String> child_buy;
    String num;
    String name;
    int count = 0;

    ListView listView;
    FirebaseDatabase data;
    DatabaseReference ref;
    ArrayList<String> list;
    ArrayAdapter<String> list_of_items;
    FirebaseListAdapter adapter;
    ImageButton imageButton2;

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home_page);

        listView = findViewById(R.id.listView);
        data = FirebaseDatabase.getInstance();
        list = new ArrayList<>();
        imageButton2 = findViewById(R.id.imageButton2);

        searchView = findViewById(R.id.searchView);

        category_buy = findViewById(R.id.category_buy);
        subcategory_buy = findViewById(R.id.subcategory_buy);

        Intent intent3 = getIntent();
        num = intent3.getStringExtra("Mobile Number");
        name = intent3.getStringExtra("Name");

        final String number = num;
        final String name_final = name;

        imageButton2.setOnClickListener(v -> {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Cart");
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    try {
                        if (snapshot.hasChild(number)) {
                            Intent intent = new Intent(HomePage.this, cart.class);
                            intent.putExtra("Number", number);
                            intent.putExtra("Name", name_final);
                            intent.putExtras(getIntent());
                            startActivity(intent);
                            finish();
                        }
                    }

                    catch (Exception e){
                        Toast.makeText(HomePage.this, e.toString() + " 1", Toast.LENGTH_SHORT).show();
                    }


                    try {
                        if (!snapshot.hasChild(number)) {
                            AlertDialog.Builder emptyCart = new AlertDialog.Builder(HomePage.this);
                            emptyCart.setMessage("Your cart is empty.")
                                    .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alertDialog = emptyCart.create();
                            alertDialog.setTitle("Information");
                            alertDialog.show();
                        }
                    }

                    catch (Exception f) {
                        Toast.makeText(HomePage.this, f.toString() + " 2", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });

        a = new ArrayList<>();
        a.add("All");
        a.add("Fresh Vegetables");
        a.add("Herbs and Seasoning");
        a.add("Fresh Fruits");
        a.add("Exotic Fruits and Veggies");
        a.add("Organic Fruits and Vegetables");
        a.add("Cuts and Sprouts");
        a.add("Flower Bouquets and Bunches");

        b = new ArrayList<>();
        b.add("All");
        b.add("Atta, Flours and Sooji");
        b.add("Dals and Pulses");
        b.add("Rice and Rice Products");
        b.add("Organic Staples");
        b.add("Salt, Sugar and Jaggery");
        b.add("Edible Oils and Ghee");
        b.add("Masalas and Spices");
        b.add("Dry Fruits");

        c = new ArrayList<>();
        c.add("All");
        c.add("Dairy");
        c.add("Breads and Buns");
        c.add("Cookies, Rusk and Khari");
        c.add("Gourmet Breads");
        c.add("Ice Creams and Desserts");
        c.add("Bakery Snacks");
        c.add("Cakes and Pastries");

        d = new ArrayList<>();
        d.add("All");
        d.add("Energy and Soft Drinks");
        d.add("Water");
        d.add("Tea");
        d.add("Coffee");
        d.add("Health Drink, Supplement");
        d.add("Fruit Juices and Drinks");

        e = new ArrayList<>();
        e.add("All");
        e.add("Noodle, Pasta, Vermicelli");
        e.add("Breakfast Cereals");
        e.add("Biscuits and Cookies");
        e.add("Frozen Veggies and Snacks");
        e.add("Spreads, Sauces and Ketchup");
        e.add("Snacks and Namkeen");
        e.add("Ready to Cook and Eat");
        e.add("Chocolates and Candies");
        e.add("Pickles and Chutney");
        e.add("Indian Mithai");

        f = new ArrayList<>();
        f.add("All");
        f.add("Feminine Hygiene");
        f.add("Oral Care");
        f.add("Bath and Hand Wash");
        f.add("Health and Medicine");
        f.add("Hair care");
        f.add("Men\'s Grooming");
        f.add("Skin Care");
        f.add("Fragrances and Deos");
        f.add("Makeup");

        g = new ArrayList<>();
        g.add("All");
        g.add("Detergents and Dishwash");
        g.add("All Purpose Cleaners");
        g.add("Disposables, Garbage Bag");
        g.add("Fresheners and Repellents");
        g.add("Mops, Brushes and Scrubs");
        g.add("Pooja Needs");
        g.add("Stationery");
        g.add("Bins and Bathroom Ware");
        g.add("Party and Festive Needs");
        g.add("Car and Shoe Care");

        h = new ArrayList<>();
        h.add("All");
        h.add("Appliances and Electricals");
        h.add("Pet Food and Accessories");
        h.add("Cookware and Non Stick");
        h.add("Kitchen Accessories");
        h.add("Gardening");
        h.add("Steel Utensils");
        h.add("Bakeware");
        h.add("Crockery and Cutlery");
        h.add("Storage and Accessories");
        h.add("Flask and Casserole");

        i = new ArrayList<>();
        i.add("All");
        i.add("Eggs");
        i.add("Poultry");
        i.add("Mutton and Lamb");
        i.add("Sausages, Bacon and Salami");
        i.add("Fish and Seafood");
        i.add("Pork and Other Meats");
        i.add("Marinades");

        j = new ArrayList<>();
        j.add("All");
        j.add("Oils and Vinegar");
        j.add("Snacks, Dry Fruits, Nuts");
        j.add("Pasta, Soup and Noodles");
        j.add("Dairy and Cheese");
        j.add("Cereals and Breakfast");
        j.add("Sauces, Spreads and Dips");
        j.add("Chocolates and Biscuits");
        j.add("Cooking and Baking Needs");
        j.add("Drinks and Beverages");
        j.add("Tinned and Processed Food");

        k = new ArrayList<>();
        k.add("All");
        k.add("Baby Food and Formula");
        k.add("Baby Bath and Hygiene");
        k.add("Diapers and Wipes");
        k.add("Mothers and Maternity");
        k.add("Feeding and Nursing");
        k.add("Baby Accessories");

        final String getSelectedText = category_buy.getSelectedItem().toString();

        category_buy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        child_buy = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, a);
                        break;
                    case 1:
                        child_buy = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, b);
                        break;
                    case 2:
                        child_buy = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, c);
                        break;
                    case 3:
                        child_buy = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, d);
                        break;
                    case 4:
                        child_buy = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, e);
                        break;
                    case 5:
                        child_buy = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, f);
                        break;
                    case 6:
                        child_buy = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, g);
                        break;
                    case 7:
                        child_buy = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, h);
                        break;
                    case 8:
                        child_buy = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, i);
                        break;
                    case 9:
                        child_buy = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, j);
                        break;
                    case 10:
                        child_buy = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, k);
                        break;
                }

                subcategory_buy.setAdapter(child_buy);

                subcategory_buy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ref = data.getReference("Grocery").child("Category").child(category_buy.getSelectedItem().toString()).child(subcategory_buy.getSelectedItem().toString());
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                list.clear();
                                try {
                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                        String itemname = ds.child("item_name").getValue(String.class);
                                        String quantity = ds.child("quantity").getValue(String.class);
                                        list.add(itemname + " " + quantity);
                                    }
                                    list_of_items = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_activated_1, list);
                                    listView.setAdapter(list_of_items);
                                }

                                catch (Exception e) {
                                    Toast.makeText(HomePage.this, e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                list_of_items.getFilter().filter(newText);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        try {
                            String Name = Objects.requireNonNull(snapshot.child(Objects.requireNonNull(list_of_items.getItem(position))).child("item_name").getValue()).toString();
                            String Brand = Objects.requireNonNull(snapshot.child(Objects.requireNonNull(list_of_items.getItem(position))).child("brand_name").getValue()).toString();
                            String Price = Objects.requireNonNull(snapshot.child(Objects.requireNonNull(list_of_items.getItem(position))).child("price").getValue()).toString();
                            String Quantity = Objects.requireNonNull(snapshot.child(Objects.requireNonNull(list_of_items.getItem(position))).child("quantity").getValue()).toString();
                            String Rating = Objects.requireNonNull(snapshot.child(Objects.requireNonNull(list_of_items.getItem(position))).child("rating").getValue()).toString();
                            String UID = Objects.requireNonNull(snapshot.child(Objects.requireNonNull(list_of_items.getItem(position))).child("uid").getValue()).toString();

                            Intent intent = new Intent(HomePage.this, item_details.class);
                            intent.putExtra("Item_Name", Name);
                            intent.putExtra("Brand", Brand);
                            intent.putExtra("Price", Price);
                            intent.putExtra("Quantity", Quantity);
                            intent.putExtra("Rating", Rating);
                            intent.putExtra("UID", UID);
                            intent.putExtra("Mobile Number", number);
                            intent.putExtras(getIntent());
                            startActivity(intent);
                            finish();
                        }

                        catch (Exception e) {
                            Toast.makeText(HomePage.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}
