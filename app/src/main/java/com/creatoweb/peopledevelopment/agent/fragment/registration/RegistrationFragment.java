package com.creatoweb.peopledevelopment.agent.fragment.registration;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableInt;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.creatoweb.peopledevelopment.agent.fragment.registration.agentmodel.AgentModel;
import com.creatoweb.peopledevelopment.agent.fragment.registration.branchmodel.BranchModel;
import com.creatoweb.peopledevelopment.agent.fragment.registration.citymodel.CityModel;
import com.creatoweb.peopledevelopment.agent.fragment.registration.statemodel.Data;
import com.creatoweb.peopledevelopment.agent.fragment.registration.statemodel.StateModel;
import com.creatoweb.peopledevelopment.R;
import com.creatoweb.peopledevelopment.agent.fragment.registration.citymodel.CityModel;
import com.creatoweb.peopledevelopment.agent.fragment.registration.statemodel.StateModel;
import com.creatoweb.peopledevelopment.data.ApiClient;
import com.creatoweb.peopledevelopment.data.AppPref;
import com.creatoweb.peopledevelopment.databinding.FragmentRegistrationBinding;
import com.creatoweb.peopledevelopment.utils.Utils;
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class RegistrationFragment extends Fragment {


    //TODO: remove some fields

    private Context context;
    private Activity activity;
    private FragmentRegistrationBinding binding;
    private ObservableInt status = new ObservableInt();

    public static final String TAG = "RegistrationFragment";

    private ArrayList<String> hide_visibe_list = new ArrayList<>();
    private List<Data> state_datalist = new ArrayList<>();
    private List<com.creatoweb.peopledevelopment.agent.fragment.registration.citymodel.Data> city_datalist = new ArrayList<>();
    private List<com.creatoweb.peopledevelopment.agent.fragment.registration.branchmodel.Data> branch_datalist = new ArrayList<>();
    private List<com.creatoweb.peopledevelopment.agent.fragment.registration.agentmodel.Data> agent_datalist = new ArrayList<>();
    private List<String> state_ArrayList = new ArrayList<>();
    private List<String> city_ArrayList = new ArrayList<>();
    private List<String> branch_ArrayList = new ArrayList<>();
    private List<String> employee_ArrayList = new ArrayList<>();
    private List<String> agent_ArrayList = new ArrayList<>();

    private String docName1 = "", docName2 = "", docName3 = "", docName4 = "", docName5 = "", docName6 = "";
    private String profile_data = "", profile_name = "", signature_data = "", signature_name = "", idproof_data = "", idproof_name = "";
    private String memberidCity = "2229", memberidState = "21", memberBranch = "", memberAgent = "", memberEmployee = "";
    private String imagedoc1_data = "", imagedoc1_name = "", imagedoc2_data = "", imagedoc2_name = "", imagedoc3_data = "", imagedoc3_name = "", imagedoc4_data = "", imagedoc4_name = "", imagedoc5_data = "", imagedoc5_name = "", imagedoc6_data = "", imagedoc6_name = "";

    private JSONArray image_array = new JSONArray();

    private String image = "";

    private int mYear, mMonth, mDay;

    private ProgressDialog progressDialog;


    public RegistrationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
        binding.setStatus(status);

        progressDialog = new ProgressDialog(activity);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("loading");

        state_ArrayList.clear();
        branch_ArrayList.clear();
        employee_ArrayList.clear();
        agent_ArrayList.clear();

        getState();
        getBranch();
        getAgent();
        termsAndCondition();

        binding.etAdddate.setEnabled(false);
        long date = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(date);
        binding.etAdddate.setText(dateString);

        binding.ivAddProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickimage(1);
            }
        });

        binding.ivAddSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setActivityTitle("Select Image")
                        .setCropShape(CropImageView.CropShape.RECTANGLE)
                        .setCropMenuCropButtonTitle("Done")
                        .setAutoZoomEnabled(true)
                        .setAspectRatio(2, 1)
                        .setCropMenuCropButtonIcon(R.drawable.done)
                        .start(context, RegistrationFragment.this);
            }
        });

        binding.ivIdProofPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickimage(3);
            }
        });

        binding.btnChoosefile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickimage(4);
            }
        });

        binding.btnChoosefile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickimage(5);
            }
        });

        binding.btnChoosefile3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickimage(6);
            }
        });

        binding.btnChoosefile4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickimage(7);
            }
        });

        binding.btnChoosefile5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickimage(8);
            }
        });

        binding.btnChoosefile6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickimage(9);
            }
        });

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                document();
            }
        });

        binding.spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (state_datalist.size() != 0) {
                    memberidState = state_datalist.get(i).getId();
                } else {
                    memberidState = "" + (i + 1);
                }
                getCity("" + (i + 1));
                String state = (String) adapterView.getItemAtPosition(i);
                Log.v("state", state);
                Log.v("state", memberidState);
                Log.v("state position", "" + i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String cityName = (String) adapterView.getItemAtPosition(i);
                if (city_datalist.size() != 0) {
                    memberidCity = city_datalist.get(i).getId();
                }
                Log.v("city", cityName);
                Log.v("city position", "" + i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        binding.spinnerBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                memberBranch=""+(i+1);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//        binding.spinnerByagent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                memberAgent = (String) binding.spinnerByagent.getItemAtPosition(i);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        binding.spinnerDocName1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                docName1 = (String) binding.spinnerDocName1.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spinnerDocName2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                docName2 = (String) binding.spinnerDocName2.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spinnerDocName3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                docName3 = (String) binding.spinnerDocName3.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spinnerDocName4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                docName4 = (String) binding.spinnerDocName4.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spinnerDocName5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                docName5 = (String) binding.spinnerDocName5.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spinnerDocName6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                docName6 = (String) binding.spinnerDocName6.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        hide_visibe_list.add("0");
        hide_visibe_list.add("0");
        hide_visibe_list.add("0");
        hide_visibe_list.add("0");
        hide_visibe_list.add("0");

        binding.tvDateofbirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                binding.tvDateofbirth.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        binding.tvAddrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hide_visibe_list.get(0).equalsIgnoreCase("0")) {
                    binding.llSeconddoc.setVisibility(View.VISIBLE);
                    hide_visibe_list.set(0, "1");
                } else if (hide_visibe_list.get(1).equalsIgnoreCase("0")) {
                    binding.llThirddoc.setVisibility(View.VISIBLE);
                    hide_visibe_list.set(1, "1");
                } else if (hide_visibe_list.get(2).equalsIgnoreCase("0")) {
                    binding.llFourthdoc.setVisibility(View.VISIBLE);
                    hide_visibe_list.set(2, "1");
                } else if (hide_visibe_list.get(3).equalsIgnoreCase("0")) {
                    binding.llFifthdoc.setVisibility(View.VISIBLE);
                    hide_visibe_list.set(3, "1");
                } else if (hide_visibe_list.get(4).equalsIgnoreCase("0")) {
                    binding.llSixthdoc.setVisibility(View.VISIBLE);
                    hide_visibe_list.set(4, "1");
                } else {
                    Toast.makeText(context, "Not Add More Than 6", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnRemove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hide_visibe_list.get(0).equalsIgnoreCase("1")) {
                    binding.llSeconddoc.setVisibility(View.GONE);
                    hide_visibe_list.set(0, "0");
                }
            }
        });

        binding.btnRemove3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hide_visibe_list.get(1).equalsIgnoreCase("1")) {
                    binding.llThirddoc.setVisibility(View.GONE);
                    hide_visibe_list.set(1, "0");
                }
            }
        });

        binding.btnRemove4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hide_visibe_list.get(2).equalsIgnoreCase("1")) {
                    binding.llFourthdoc.setVisibility(View.GONE);
                    hide_visibe_list.set(2, "0");
                }
            }
        });

        binding.btnRemove5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hide_visibe_list.get(3).equalsIgnoreCase("1")) {
                    binding.llFifthdoc.setVisibility(View.GONE);
                    hide_visibe_list.set(3, "0");
                }
            }
        });
        binding.btnRemove6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hide_visibe_list.get(4).equalsIgnoreCase("1")) {
                    binding.llSixthdoc.setVisibility(View.GONE);
                    hide_visibe_list.set(4, "0");
                }
            }
        });

        return binding.getRoot();
    }

    private void termsAndCondition() {
        String text = "your personal detail are secure with us. By Proceeding further you agree to our Terms & Conditions.";

        SpannableString spannableString = new SpannableString(text);

        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Navigation.findNavController(widget).navigate(R.id.termsAndConditionFragment);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };

        spannableString.setSpan(clickableSpan1, 80, 98, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        binding.tvTermsandcondition.setText(spannableString);
        binding.tvTermsandcondition.setMovementMethod(LinkMovementMethod.getInstance());

    }

    private void getState() {
        Call<StateModel> call = ApiClient.getApiClient().getState();
        call.enqueue(new Callback<StateModel>() {
            @Override
            public void onResponse(Call<StateModel> call, Response<StateModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    StateModel stateModel = response.body();

                    if (stateModel.getMessage().equalsIgnoreCase("success!")) {
                        state_datalist = stateModel.getData();

                        state_ArrayList.clear();

                        for (int i = 0; i < state_datalist.size(); i++) {
                            state_ArrayList.add(state_datalist.get(i).getName());
                        }


                        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, state_ArrayList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.spinnerState.setAdapter(adapter);

                        if (state_datalist.size() != 0) {
                            memberidState = state_datalist.get(0).getId();
                        }
                        getCity("1");
                    } else if (stateModel.getMessage().equalsIgnoreCase("No Record Found!")) {
                        Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<StateModel> call, Throwable t) {
                Log.e("State Api..", "......FAIL....." + t.toString() + ".............");
                Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCity(String stateId) {
        Call<CityModel> call = ApiClient.getApiClient().getCity(stateId);
        call.enqueue(new Callback<CityModel>() {
            @Override
            public void onResponse(Call<CityModel> call, Response<CityModel> response) {

                if (response.isSuccessful() && response.body() != null) {
                    CityModel stateModel = response.body();
                    if (stateModel.getMessage().equalsIgnoreCase("success!")) {
                        city_datalist = stateModel.getData();

                        city_ArrayList.clear();

                        for (int i = 0; i < city_datalist.size(); i++) {
                            city_ArrayList.add(city_datalist.get(i).getName());

                        }


                        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, city_ArrayList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.spinnerCity.setAdapter(adapter);

                        if (city_datalist.size() != 0) {
                            memberidCity = city_datalist.get(0).getId();
                        }

                    } else if (stateModel.getMessage().equalsIgnoreCase("No Record Found!")) {
                        Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<CityModel> call, Throwable t) {
                Log.e("City Api..", "......FAIL....." + t.toString() + ".............");
                Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getBranch() {
        Call<BranchModel> call = ApiClient.getApiClient().getBranch();
        call.enqueue(new Callback<BranchModel>() {
            @Override
            public void onResponse(Call<BranchModel> call, Response<BranchModel> response) {

                if (response.isSuccessful() && response.body() != null) {
                    BranchModel branchModel = response.body();
                    if (branchModel.getMessage().equalsIgnoreCase("success!")) {
                        branch_datalist = branchModel.getData();

                        branch_ArrayList.clear();

                        for (int i = 0; i < branch_datalist.size(); i++) {
                            branch_ArrayList.add(branch_datalist.get(i).getTouchbeeBranchName());
                            memberBranch = branch_datalist.get(0).getTouchbeeBranchId();
                            Log.e("Branch Api..", "..........." + branch_datalist.get(i).getTouchbeeBranchName() + ".............");
                        }

//                        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, branch_ArrayList);
//                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        binding.spinnerBranch.setAdapter(adapter);

                    } else if (branchModel.getMessage().equalsIgnoreCase("No Record Found!")) {
                        Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<BranchModel> call, Throwable t) {
                Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
                Log.e("Branch Api..", "......FAIL....." + t.toString() + ".............");
            }
        });
    }

    private void getAgent() {
        Call<AgentModel> call = ApiClient.getApiClient().getAgentList();
        call.enqueue(new Callback<AgentModel>() {
            @Override
            public void onResponse(Call<AgentModel> call, Response<AgentModel> response) {

                if (response.isSuccessful() && response.body() != null) {
                    AgentModel agentModel = response.body();
                    if (agentModel.getMessage().equalsIgnoreCase("success!")) {
                        agent_datalist = agentModel.getData();

                        agent_ArrayList.clear();

                        for (int i = 0; i < agent_datalist.size(); i++) {
                            agent_ArrayList.add(agent_datalist.get(i).getEmployeeName());
                        }

//                        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, agent_ArrayList);
//                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        binding.spinnerByagent.setAdapter(adapter);

                        memberAgent = agent_datalist.get(0).getEmployeeName();

                    } else if (agentModel.getMessage().equalsIgnoreCase("No Record Found!")) {
                        Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<AgentModel> call, Throwable t) {
                Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendRegistration(View view) {
        progressDialog.show();
        if (binding.etMembername.getText().toString().equals("")) {
            binding.etMembername.setError("Enter Member Name");
        } else if (binding.etFathername.getText().toString().equals("")) {
            binding.etFathername.setError("Enter Father/Husband Name");
        } else if (binding.tvDateofbirth.getText().toString().equals("")) {
            binding.tvDateofbirth.setError("Enter Date of Birth");
        } else if (binding.etAddress.getText().toString().equals("")) {
            binding.etAddress.setError("Enter Member Address");
        } else if (idproof_data.equals("")) {
            Toast.makeText(context, "Please Select Id Proff Picture", Toast.LENGTH_SHORT).show();
        } else if (signature_data.equals("")) {
            Toast.makeText(context, "Please Select Signature Picture", Toast.LENGTH_SHORT).show();
        } else if (profile_data.equals("")) {
            Toast.makeText(context, "Please Select Profile Picture", Toast.LENGTH_SHORT).show();
        } else if (image_array.length() == 0) {
            Toast.makeText(context, "Please Select atleast one Document", Toast.LENGTH_SHORT).show();
        } else if (binding.etIdProffNo.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(context, "Please Enter Id proff No.", Toast.LENGTH_SHORT).show();
        } else {
            AppPref appPref = AppPref.getInstance(activity);
            String agent_id = appPref.getString(AppPref.Key.ID, "");
            String agent_name = appPref.getString(AppPref.Key.NAME, "");
            Log.v(TAG, "...etMembername..." + binding.etMembername.getText().toString());
            Log.v(TAG, "etFathername..." + binding.etFathername.getText().toString());
            Log.v(TAG, "etMemberemail... " + binding.etMemberemail.getText().toString());
            Log.v(TAG, "etContact1... " + binding.etContact1.getText().toString());
            Log.v(TAG, "etContact2... " + binding.etContact2.getText().toString());
            Log.v(TAG, "etAddress..." + binding.etAddress.getText().toString());
            Log.v(TAG, "memberCity... " + memberidCity);
            Log.v(TAG, "memberState... " + memberidState);
            Log.v(TAG, "etAdddate..." + binding.etAdddate.getText().toString());
            Log.v(TAG, "profile_name... " + profile_name);
            Log.v(TAG, "signature_name... " + signature_name);
            Log.v(TAG, "idproof_name..." + idproof_name);
            Log.v(TAG, "etNominee..." + binding.etNominee.getText().toString());
            Log.v(TAG, "etAge..." + binding.etAge.getText().toString());
            Log.v(TAG, "memberAgent..." + agent_id + "......" + agent_name);
            Log.v(TAG, "memberBranch..." + memberBranch);
            Log.v(TAG, "etRelations..." + binding.etRelations.getText().toString());
            Log.v(TAG, "tvDateofbirth..." + binding.tvDateofbirth.getText().toString());
            Log.v(TAG, "idproof_data ..." + idproof_data);
            Log.v(TAG, "signature_data ..." + signature_data);
            Log.v(TAG, "profile_data..." + profile_data);
            Log.v(TAG, "image_array..." + image_array);

            Call<RegistrationModel> call = ApiClient.getApiClient().sendRegistration(
                    binding.etMembername.getText().toString(),
                    binding.etFathername.getText().toString(),
                    binding.etMemberemail.getText().toString(),
                    binding.etContact1.getText().toString(),
                    binding.etContact2.getText().toString(),
                    binding.etAddress.getText().toString(),
                    memberidCity,
                    "abc", memberidState,
                    binding.etAdddate.getText().toString(),
                    profile_name,
                    signature_name,
                    idproof_name,
                    binding.etNominee.getText().toString(),
                    binding.etAge.getText().toString(),
                    agent_id, memberBranch,
                    "10",
                    binding.etRelations.getText().toString(),
                    binding.etIdProffNo.getText().toString(),
                    binding.tvDateofbirth.getText().toString(),
                    idproof_data,
                    signature_data,
                    profile_data,
                    "" + image_array);

            call.enqueue(new Callback<RegistrationModel>() {
                @Override
                public void onResponse(Call<RegistrationModel> call, Response<RegistrationModel> response) {
                    progressDialog.hide();

                    if (response.isSuccessful() && response.body() != null) {
                        Log.e(TAG, ".........." + response.code() + "........" + response.message() + "..........");
                        RegistrationModel registrationModel = response.body();
                        if (registrationModel.getMessage().equalsIgnoreCase("Success!")) {
                            Boolean data = registrationModel.getData();

                            if (data.equals(true)) {
                                Log.e(TAG, "..........true.............");

                                Bundle bundle = new Bundle();
                                bundle.putString("name", binding.etMembername.getText().toString());
                                bundle.putString("memberActId", registrationModel.getMemberActId());
                                bundle.putString("amount", " Rs 10");
                                bundle.putString("percent", "Not");
                                bundle.putString("accounttype", "Registration");
                                bundle.putString("image", image);

                                Navigation.findNavController(view).navigate(R.id.pdfFragment, bundle);

                                Toast.makeText(context, "Registration Sucessfully...", Toast.LENGTH_SHORT).show();
                            }

                        } else if (registrationModel.getMessage().equalsIgnoreCase("No Record Found!")) {

                            Toast.makeText(context, "Not able to Register", Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(context, "Mobile already exist for this Registration!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.v(TAG,response.toString());
                        Toast.makeText(context, "Not Submit Data Sucessfully", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RegistrationModel> call, Throwable t) {
                    progressDialog.hide();
                    Log.e(TAG, "..........Fail......" + t.toString() + "..............");
                    Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void document() {
        progressDialog.show();

        image_array = new JSONArray();
        JSONObject image_object1 = new JSONObject();
        try {
            image_object1.put("document_imgname", imagedoc1_name);
            image_object1.put("document_data", imagedoc1_data);
            image_object1.put("doc_name", docName1);
            image_object1.put("doc_no", binding.etDocumentno1.getText().toString());
            if (!imagedoc1_data.equalsIgnoreCase("")) {
                image_array.put(image_object1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (hide_visibe_list.get(0).equalsIgnoreCase("1")) {
            JSONObject image_object = new JSONObject();
            try {
                image_object.put("document_imgname", imagedoc2_name);
                image_object.put("document_data", imagedoc2_data);
                image_object.put("doc_name", docName2);
                image_object.put("doc_no", binding.etDocumentno2.getText().toString());
                if (!imagedoc2_data.equalsIgnoreCase("")) {
                    image_array.put(image_object);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (hide_visibe_list.get(1).equalsIgnoreCase("1")) {
            JSONObject image_object = new JSONObject();
            try {
                image_object.put("document_imgname", imagedoc3_name);
                image_object.put("document_data", imagedoc3_data);
                image_object.put("doc_name", docName3);
                image_object.put("doc_no", binding.etDocumentno3.getText().toString());
                if (!imagedoc3_data.equalsIgnoreCase("")) {
                    image_array.put(image_object);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (hide_visibe_list.get(2).equalsIgnoreCase("1")) {
            JSONObject image_object = new JSONObject();
            try {
                image_object.put("document_imgname", imagedoc4_name);
                image_object.put("document_data", imagedoc4_data);
                image_object.put("doc_name", docName4);
                image_object.put("doc_no", binding.etDocumentno4.getText().toString());
                if (!imagedoc4_data.equalsIgnoreCase("")) {
                    image_array.put(image_object);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (hide_visibe_list.get(3).equalsIgnoreCase("1")) {
            JSONObject image_object = new JSONObject();
            try {
                image_object.put("document_imgname", imagedoc5_name);
                image_object.put("document_data", imagedoc5_data);
                image_object.put("doc_name", docName5);
                image_object.put("doc_no", binding.etDocumentno5.getText().toString());
                if (!imagedoc5_data.equalsIgnoreCase("")) {
                    image_array.put(image_object);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (hide_visibe_list.get(4).equalsIgnoreCase("1")) {
            JSONObject image_object = new JSONObject();
            try {
                image_object.put("document_imgname", imagedoc6_name);
                image_object.put("document_data", imagedoc6_data);
                image_object.put("doc_name", docName6);
                image_object.put("doc_no", binding.etDocumentno6.getText().toString());
                if (!imagedoc6_data.equalsIgnoreCase("")) {
                    image_array.put(image_object);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.v("tag", "" + image_array);
        Log.v("tag", "" + image_array.length());
        sendRegistration(binding.submit);
    }

    //use for pic image form gallary
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (data != null) {
                //binding.addPhoto.setImageURI(result.getUri());
                Uri uri = result.getUri();
                try {
                    final Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
//                    binding.cropImageView.setImageBitmap(bitmap);
                    signature_data = Utils.getEncoded64ImageStringFromBitmap(bitmap);
                    signature_name = Utils.generateImageName("Cheque");
                    image = BitMapToString(bitmap);
                    binding.addSignature.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (resultCode == RESULT_OK) {
            List<String> pic = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
            if (pic != null) {
                if (pic.size() > 0) {
                    File f = new File(pic.get(0));
                    Bitmap d = new BitmapDrawable(getResources(), f.getAbsolutePath()).getBitmap();
                    switch (requestCode) {
                        case 1:
                            profile_data = Utils.getEncoded64ImageStringFromBitmap(d);
                            profile_name = f.getName();
                            binding.addPhoto.setImageBitmap(d);
                            break;
                        case 2:
//                        signature_data = Utils.getEncoded64ImageStringFromBitmap(d);
//                        signature_name = f.getName();
//                        image = BitMapToString(d);
//                        binding.addSignature.setImageBitmap(d);
                            break;
                        case 3:
                            idproof_data = Utils.getEncoded64ImageStringFromBitmap(d);
                            idproof_name = f.getName();
                            binding.addIdproffpic.setImageBitmap(d);
                            break;
                        case 4:
                            imagedoc1_data = Utils.getEncoded64ImageStringFromBitmap(d);
                            imagedoc1_name = f.getName();
                            binding.tvFilename1.setText(f.getName());
                            break;
                        case 5:
                            imagedoc2_data = Utils.getEncoded64ImageStringFromBitmap(d);
                            imagedoc2_name = f.getName();
                            binding.tvFilename2.setText(f.getName());
                            break;
                        case 6:
                            imagedoc3_data = Utils.getEncoded64ImageStringFromBitmap(d);
                            imagedoc3_name = f.getName();
                            binding.tvFilename3.setText(f.getName());
                            break;
                        case 7:
                            imagedoc4_data = Utils.getEncoded64ImageStringFromBitmap(d);
                            imagedoc4_name = f.getName();
                            binding.tvFilename4.setText(f.getName());
                            break;
                        case 8:
                            imagedoc5_data = Utils.getEncoded64ImageStringFromBitmap(d);
                            imagedoc5_name = f.getName();
                            binding.tvFilename5.setText(f.getName());
                            break;
                        case 9:
                            imagedoc6_data = Utils.getEncoded64ImageStringFromBitmap(d);
                            imagedoc6_name = f.getName();
                            binding.tvFilename6.setText(f.getName());
                            break;

                    }

                    Log.v(TAG, "" + requestCode);
                }
            }
        }
    }

    public static String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public void pickimage(int position) {
        Options options = Options.init()
                .setRequestCode(position)
                .setCount(1);
        Log.v(TAG, "" + position);
        Pix.start(this, options);
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        this.context = activity;
        this.activity = activity;
    }
}
