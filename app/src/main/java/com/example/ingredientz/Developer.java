package com.example.ingredientz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;

public class Developer extends AppCompatActivity {

    Button add_button;
    EditText Name, Brand, Price, Quantity;
    Spinner categories, subcategory;
    ArrayList<String> a, b, c, d, e, f, g, h, i, j, k;
    ArrayAdapter<String> child;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    DatabaseReference reference2;

    Random rand = new Random();
    int UID = rand.nextInt(1000000000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

        add_button = findViewById(R.id.add_button);
        Name = findViewById(R.id.item_name);
        Brand = findViewById(R.id.brand_name);
        Price = findViewById(R.id.price);
        Quantity = findViewById(R.id.item_quantity);
        categories = findViewById(R.id.categories);
        subcategory = findViewById(R.id.subcategory);

        a = new ArrayList<>();
        a.add("Fresh Vegetables");
        a.add("Herbs and Seasoning");
        a.add("Fresh Fruits");
        a.add("Exotic Fruits and Veggies");
        a.add("Organic Fruits and Vegetables");
        a.add("Cuts and Sprouts");
        a.add("Flower Bouquets and Bunches");

        b = new ArrayList<>();
        b.add("Atta, Flours and Sooji");
        b.add("Dals and Pulses");
        b.add("Rice and Rice Products");
        b.add("Organic Staples");
        b.add("Salt, Sugar and Jaggery");
        b.add("Edible Oils and Ghee");
        b.add("Masalas and Spices");
        b.add("Dry Fruits");

        c = new ArrayList<>();
        c.add("Dairy");
        c.add("Breads and Buns");
        c.add("Cookies, Rusk and Khari");
        c.add("Gourmet Breads");
        c.add("Ice Creams and Desserts");
        c.add("Bakery Snacks");
        c.add("Cakes and Pastries");

        d = new ArrayList<>();
        d.add("Energy and Soft Drinks");
        d.add("Water");
        d.add("Tea");
        d.add("Coffee");
        d.add("health Drink, Supplement");
        d.add("Fruit Juices and Drinks");

        e = new ArrayList<>();
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
        i.add("Eggs");
        i.add("Poultry");
        i.add("Mutton and Lamb");
        i.add("Sausages, Bacon and Salami");
        i.add("Fish and Seafood");
        i.add("Pork and Other Meats");
        i.add("Marinades");

        j = new ArrayList<>();
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
        k.add("Baby Food and Formula");
        k.add("Baby Bath and Hygiene");
        k.add("Diapers and Wipes");
        k.add("Mothers and Maternity");
        k.add("Feeding and Nursing");
        k.add("Baby Accessories");

        final String getSelectedText = categories.getSelectedItem().toString();

        categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        child = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, a);
                        break;
                    case 1:
                        child = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, b);
                        break;
                    case 2:
                        child = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, c);
                        break;
                    case 3:
                        child = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, d);
                        break;
                    case 4:
                        child = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, e);
                        break;
                    case 5:
                        child = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, f);
                        break;
                    case 6:
                        child = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, g);
                        break;
                    case 7:
                        child = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, h);
                        break;
                    case 8:
                        child = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, i);
                        break;
                    case 9:
                        child = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, j);
                        break;
                    case 10:
                        child = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, k);
                        break;
                }

                subcategory.setAdapter(child);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nameOfItem = Name.getText().toString().trim();
                final String nameOfBrand = Brand.getText().toString().trim();
                final String priceOfItem = Price.getText().toString().trim();
                final String quantityOfItem = Quantity.getText().toString().trim();
                Random rand = new Random();
                int UID = rand.nextInt(1000000000);

                if(nameOfItem.equals("")){
                    Toast.makeText(Developer.this, "Please Enter Item Name", Toast.LENGTH_SHORT).show();
                }

                else {
                    final DatabaseReference[] reference = {FirebaseDatabase.getInstance().getReference().child("Grocery").child("Category").child(categories.getSelectedItem().toString()).child("All")};
                    Query checkItem = reference[0].orderByChild(String.valueOf("item_name" + " " + "quantity")).equalTo(nameOfItem + " " + quantityOfItem);

                    checkItem.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                Toast.makeText(Developer.this, "Item already exists.", Toast.LENGTH_SHORT).show();
                            }

                            else{
                                rootNode = FirebaseDatabase.getInstance();
                                reference[0] = rootNode.getReference().child("Grocery").child("Category").child(categories.getSelectedItem().toString()).child(subcategory.getSelectedItem().toString());
                                reference2 = rootNode.getReference().child("Grocery").child("Category").child(categories.getSelectedItem().toString()).child("All");

                                DevHelperClass helperClass = new DevHelperClass(nameOfItem, nameOfBrand, priceOfItem, quantityOfItem, Integer.toString(0), Integer.toString(UID));
                                reference[0].child(String.valueOf(nameOfItem + " " + quantityOfItem)).setValue(helperClass);


                                reference2.child(String.valueOf(nameOfItem + " " + quantityOfItem)).setValue(helperClass);
                                Toast.makeText(Developer.this, "Item added successfully", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
}