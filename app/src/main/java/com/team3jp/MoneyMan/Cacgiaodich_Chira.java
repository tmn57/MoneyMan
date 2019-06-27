package com.team3jp.MoneyMan;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.team3jp.MoneyMan.Items.Logs;
import com.team3jp.MoneyMan.Items.ObjectThuchi;
import com.team3jp.MoneyMan.Items.Wallet;
import com.team3jp.MoneyMan.Utils.CurrencyUltils;

import java.util.ArrayList;
import java.util.List;

public class Cacgiaodich_Chira extends Fragment {

    ListView mylist;
    ArrayList<ObjectThuchi> arrayList = new ArrayList<ObjectThuchi>();

    private static final String TAG = "Cacgiaodich_Chira";
    Spinner spinner;
    CurrencyUltils currencyUltils = new CurrencyUltils();
    String[] data;
    String name = "", sdate = "", edate = "";
    int pos;
    public static Cacgiaodich_Chira newInstance() {
        Cacgiaodich_Chira fragment = new Cacgiaodich_Chira();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RelativeLayout linearLayout = (RelativeLayout) inflater.inflate(R.layout.cacgiaodich_chira, null);

        //
        spinner = (Spinner) linearLayout.findViewById(R.id.spn_cackhoanchira);
        data = getActivity().getResources().getStringArray(R.array.list_giaodich_cacluachon);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spin, R.id.tvspin, data);


        spinner.setAdapter(adapter);
        spinner.setSelection(1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) loadFragment(new Cacgiaodich_Thuvao());
                else if (position == 2) loadFragment(new Cacgiaodich_Chovay());
                else if (position == 3) loadFragment(new Cacgiaodich_Vay());
                else if (position == 4) loadFragment(new Cacgiaodich_Cacgiaodichganday());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //

        Bundle bundle = getArguments();

        if (bundle != null) {
            // name = bundle.getString("tenvi", "");
            pos = bundle.getInt("pos");
            sdate = bundle.getString("ngaybatdau", "");
            edate = bundle.getString("ngayketthuc", "");
        }

        final DatabaseHandler databaseHandler = MoneyManApplication.getDatabase();


        int ID_Wallet, size;
        size = databaseHandler.getAllWallet().size();
        if (pos>size){
            pos = size;
            ID_Wallet = 0;
        }
        else ID_Wallet = databaseHandler.getAllWallet().get(pos).getID();


        List<Logs> logsList = databaseHandler.getAllLogs();

        for (int i = 0; i < logsList.size(); i++) {
            String date = logsList.get(i).getLog_datetime();
            date = date.substring(0, 10);

            if (logsList.get(i).getLog_action() == 2 && name.equals("") == true && sdate.equals("") == true
                    && edate.equals("") == true) {        // 2: chi ra
                String des = logsList.get(i).getLog_description();
                double money = logsList.get(i).getLog_money();


                String unit = logsList.get(i).getLog_unit();
                if (unit == null) unit = "";
                Wallet wallet = databaseHandler.getWallet(logsList.get(i).getLog_id());
                String name_wallet = wallet.getName();

                String datetime = logsList.get(i).getLog_datetime();

                ObjectThuchi objectThuchi = new ObjectThuchi(R.drawable.ic_call_made_black_24dp,
                        des, "Từ: " + name_wallet, currencyUltils.formatCurrencyWithSymbol(money,unit), datetime);

                arrayList.add(objectThuchi);

            } else if (logsList.get(i).getLog_action() == 2 && pos == size) {
                if ((date.equals(sdate) == true || date.compareTo(sdate) > 0) &&
                        (date.equals(edate) == true || date.compareTo(edate) < 0)) {
                    String des = logsList.get(i).getLog_description();
                    double money = logsList.get(i).getLog_money();


                    String unit = logsList.get(i).getLog_unit();
                    if (unit == null) unit = "";
                    Wallet wallet = databaseHandler.getWallet(logsList.get(i).getLog_id());
                    String name_wallet = wallet.getName();

                    String datetime = logsList.get(i).getLog_datetime();

                    ObjectThuchi objectThuchi = new ObjectThuchi(R.drawable.ic_call_made_black_24dp,
                            des, "Từ: " + name_wallet, currencyUltils.formatCurrencyWithSymbol(money,unit), datetime);

                    arrayList.add(objectThuchi);
                }

            } else if (logsList.get(i).getLog_action() == 2 && pos != size) {
                if (ID_Wallet == logsList.get(i).getLog_id() && (date.equals(sdate) == true || date.compareTo(sdate) > 0) &&
                        (date.equals(edate) == true || date.compareTo(edate) < 0)) {
                    String des = logsList.get(i).getLog_description();
                    double money = logsList.get(i).getLog_money();


                    String unit = logsList.get(i).getLog_unit();
                    if (unit == null) unit = "";
                    Wallet wallet = databaseHandler.getWallet(logsList.get(i).getLog_id());
                    String name_wallet = wallet.getName();

                    String datetime = logsList.get(i).getLog_datetime();

                    ObjectThuchi objectThuchi = new ObjectThuchi(R.drawable.ic_call_made_black_24dp,
                            des, "Từ: " + name_wallet, currencyUltils.formatCurrencyWithSymbol(money,unit), datetime);

                    arrayList.add(objectThuchi);
                }

            }
        }

        mylist = (ListView) linearLayout.findViewById(R.id.list_cacgiaodich_chira);

        final AdapterObjectThuchi adapterObjectThuchi = new AdapterObjectThuchi(getActivity(),
                R.layout.custom_row_cacgiaodich, arrayList);
        mylist.setAdapter(adapterObjectThuchi);


        FloatingActionButton fab = (FloatingActionButton) linearLayout.findViewById(R.id.fab_Loc_Chira);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1 = new Bundle();
                Cacgiaodich_Loc cacgiaodich_loc = new Cacgiaodich_Loc();
                bundle1.putString("ten_fragment", "chira");
                cacgiaodich_loc.setArguments(bundle1);
                cacgiaodich_loc.show(getFragmentManager(), "");

            }
        });

        return linearLayout;
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
