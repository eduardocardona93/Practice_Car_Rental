package com.example.practice_car_rental;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static Spinner makeSp, carSp;
    public static ImageView carImg;
    public static CheckBox navigatorChk, childSeatChk, bikeRakeChk;
    public static RadioButton  age1Rb, age2Rb, age3Rb;
    public static EditText daysFld;
    public static TextView dailyRateLbl, currentMileageLbl, amountLbl;
    public static Button okBtn;

    public static String makes[] = {"Ford","Nissan","Toyota","Dodge","Honda"};
    public ArrayList<Car> tempCarList = new ArrayList<>();
    public ArrayList<Car> cars = new ArrayList<>();
    public ArrayList<String> temp = new ArrayList<>();
    public double ageDailyRate = 0.0, optionsDailyRate = 0.0, totalAmount = 0.0, dailyCarRate = 0.0, totalDaily = 0.0;
    public int carIndex = 0, makeIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fillCarList();



        carImg = findViewById(R.id.carImg);

        //checkboxes
        navigatorChk = findViewById(R.id.navigatorChk);
        navigatorChk.setOnCheckedChangeListener(new OptionsCheckButtonEvent());
        childSeatChk = findViewById(R.id.childSeatChk);
        childSeatChk.setOnCheckedChangeListener(new OptionsCheckButtonEvent());
        bikeRakeChk = findViewById(R.id.bikeRakeChk);
        bikeRakeChk.setOnCheckedChangeListener(new OptionsCheckButtonEvent());
        //radio buttons

        age1Rb = findViewById(R.id.age1Rb);
        age1Rb.setOnClickListener(new AgeRadioButtonEvents());
        ageDailyRate = 30.0;
        age1Rb.setChecked(true);
        age2Rb = findViewById(R.id.age2Rb);
        age2Rb.setOnClickListener(new AgeRadioButtonEvents());
        age3Rb = findViewById(R.id.age3Rb);
        age3Rb.setOnClickListener(new AgeRadioButtonEvents());
        
        daysFld = findViewById(R.id.daysFld);
        daysFld.setText("1");

        dailyRateLbl = findViewById(R.id.dailyRateLbl);
        currentMileageLbl = findViewById(R.id.currentMileageLbl);
        amountLbl = findViewById(R.id.amountLbl);

        // submit
        okBtn = findViewById(R.id.okBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalAmount = (totalDaily * Double.parseDouble(daysFld.getText().toString()) * 1.13) ;
                amountLbl.setText("$" + String.format("%.2f", totalAmount));
            }
        });
        
        // spinners
        makeSp = findViewById(R.id.makeSp);
        carSp = findViewById(R.id.carSp);

        ArrayAdapter aa1=new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,makes);
        makeSp.setAdapter(aa1);

        makeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                makeIndex = position;
                fillTemp();
                ArrayAdapter aa2=new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_dropdown_item,temp);
                carSp.setAdapter(aa2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                makeIndex = 0;
                fillTemp();
                ArrayAdapter aa2=new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_dropdown_item,temp);
                carSp.setAdapter(aa2);
            }
        });
        carSp.setOnItemSelectedListener(new carSelectedEvent());
    }

    public void fillCarList(){
        cars.add(new Car(makes[0],"Mustang",200.0,3450, "Mustang".toLowerCase()));
        cars.add(new Car(makes[0],"Edge",70.0,8253, "Edge".toLowerCase()));
        cars.add(new Car(makes[0],"Focus",30.0,13367, "Focus".toLowerCase()));
        cars.add(new Car(makes[0],"Escape",50.0,10671, "Escape".toLowerCase()));
        cars.add(new Car(makes[1],"Sunny",30.0,10231, "Sunny".toLowerCase()));
        cars.add(new Car(makes[1],"Rogue",60.0,4219, "Rogue".toLowerCase()));
        cars.add(new Car(makes[1],"Kiks",45.0,3201, "Kiks".toLowerCase()));
        cars.add(new Car(makes[1],"Pathfinder",90.0,2034, "Pathfinder".toLowerCase()));
        cars.add(new Car(makes[1],"Murano",75.0,8501, "Murano".toLowerCase()));
        cars.add(new Car(makes[2],"Camry",45.0,20341, "Camry".toLowerCase()));
        cars.add(new Car(makes[2],"Corolla",35.0,12345, "Corolla".toLowerCase()));
        cars.add(new Car(makes[2],"Yaris",30.0,30348, "Yaris".toLowerCase()));
        cars.add(new Car(makes[3],"Charger",80.0,8753, "Charger".toLowerCase()));
        cars.add(new Car(makes[3],"Caravan",130.0,9023, "Caravan".toLowerCase()));
        cars.add(new Car(makes[3],"Journey",70.0,23901, "Journey".toLowerCase()));
        cars.add(new Car(makes[4],"City",30.0,3049, "City".toLowerCase()));
        cars.add(new Car(makes[4],"Civic",35.0,7401, "Civic".toLowerCase()));
        cars.add(new Car(makes[4],"Accord",60.0,7912, "Accord".toLowerCase()));
    }

    public void fillTemp() {
        tempCarList.clear();
        temp.clear();

        for (Car car : cars) {
            if (car.getMake().equalsIgnoreCase(makes[makeIndex])){
                tempCarList.add(car);
                temp.add(car.getName());
            }
        }
    }

    public void calculateDailyRate(){
        dailyCarRate = tempCarList.get(carIndex).getDailyRate();
        currentMileageLbl.setText(String.valueOf( tempCarList.get(carIndex).getCurrentMileage()));
        int imgId=getResources().getIdentifier(tempCarList.get(carIndex).getImg(),"mipmap",getPackageName());
        carImg.setImageResource(imgId);
        totalDaily = ageDailyRate + optionsDailyRate + dailyCarRate;
        dailyRateLbl.setText("$" + String.format("%.2f", totalDaily));
    }

    private class AgeRadioButtonEvents implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.age1Rb:
                    ageDailyRate = 30.0;
                    break;
                case R.id.age2Rb:
                    ageDailyRate = 17.0;
                    break;
                case R.id.age3Rb:
                    ageDailyRate = 22.0;
                    break;
                default:
                    ageDailyRate = 0.0;
                    break;
            }
            calculateDailyRate();
        }
    }

    private class OptionsCheckButtonEvent implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            optionsDailyRate = 0.0;
            if(navigatorChk.isChecked())
                optionsDailyRate += 5.0;
            if(childSeatChk.isChecked())
                optionsDailyRate += 7.0;
            if(bikeRakeChk.isChecked())
                optionsDailyRate += 10.0;

            calculateDailyRate();
        }
    }

    private class carSelectedEvent implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            carIndex = position;
            calculateDailyRate();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            carIndex = 0;
            calculateDailyRate();
        }
    }
}