package edu.byu.cs.tweeter.view.main.LoginRegister;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.presenter.RegisterPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.RegisterTask;
import edu.byu.cs.tweeter.view.main.MainActivity;

import static android.app.Activity.RESULT_OK;

public class RegisterFragment extends Fragment implements RegisterPresenter.View, RegisterTask.Observer{
    public RegisterFragment() {}
    private static final String LOG_TAG = "registerFragment";
    private RegisterPresenter presenter;
    private Toast registerInToast;
    private Toast uploadImageToast = null;

    // Photo stuff
    public byte[] byteArray;
    private Bitmap photo;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView imageView;

    // Text variables
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;

    private String firstName;
    private String lastName;
    private String alias;
    private String password;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        presenter = new RegisterPresenter(this);

        firstNameEditText = view.findViewById(R.id.et_firstName);
        lastNameEditText = view.findViewById(R.id.et_lastName);
        usernameEditText = view.findViewById(R.id.et_username2);
        passwordEditText = view.findViewById(R.id.et_password2);

        imageView = (ImageView) view.findViewById(R.id.imageView);

        // PHOTO CAPTURE BUTTON - This is how to do it: https://developer.android.com/training/camera/photobasics
        Button takeProfilePictureButton = view.findViewById(R.id.TakeProfilePictureButton);
        takeProfilePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (uploadImageToast != null) {
                    uploadImageToast.cancel();
                }

                try {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                } catch (ActivityNotFoundException e) {
                    // display error state to the user
                }
            }
        });


        // DEFINES THE REGISTER BUTTON
        Button registerButton = view.findViewById(R.id.RegisterButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Makes a register request. The user is hard-coded, so it doesn't matter what data we put
             * in the RegisterRequest object.
             *
             * @param view the view object that was clicked.
             */
            @Override
            public void onClick(View view) {

                if(byteArray == null) {
                    uploadImageToast = Toast.makeText(getActivity(), "Upload an image first!", Toast.LENGTH_LONG); // getActivity().getBaseContext()
                    uploadImageToast.show();
                }
                else {
                    registerInToast = Toast.makeText(getActivity(), "You are Registered!", Toast.LENGTH_LONG); // getActivity().getBaseContext()
                    registerInToast.show();

                    firstName = firstNameEditText.getText().toString();
                    lastName = lastNameEditText.getText().toString();
                    alias = usernameEditText.getText().toString();
                    password = passwordEditText.getText().toString();

                    RegisterTask registerTask = new RegisterTask(presenter, RegisterFragment.this);
                    registerTask.execute();
                }
            }
        });

        return view;
//        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void registerSuccessful(RegisterResponse registerResponse) {
        Intent intent = new Intent(getActivity().getBaseContext(), MainActivity.class);

        // https://stackoverflow.com/questions/2091465/how-do-i-pass-data-between-activities-in-android-application
        intent.putExtra(MainActivity.CURRENT_USER_KEY, registerResponse.getUser()); // Original method
        intent.putExtra("CURRENT_USER_KEY", registerResponse.getUser());
        intent.putExtra("AUTH_TOKEN_KEY", registerResponse.getAuthToken());

        registerInToast.cancel();

        // This clears the textview fields.
        firstNameEditText.setText("");
        lastNameEditText.setText("");
        usernameEditText.setText("");
        passwordEditText.setText("");

        startActivity(intent);
    }

    @Override
    public void registerUnsuccessful(RegisterResponse registerResponse) {
        Toast.makeText(getActivity(), "Failed to register. " + registerResponse.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void handleException(Exception ex) {
        Log.e(LOG_TAG, ex.getMessage(), ex);
        Toast.makeText(getActivity(), "Failed to register because of exception: " + ex.getMessage(), Toast.LENGTH_LONG).show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            photo = (Bitmap) extras.get("data");

            imageView.setImageBitmap(photo);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
        }
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String getPassword() {
        return password;
    }

    /**
     * This encodes the byte[] byteArray to and String encodedByteArray
     * Link on how to encode and decode.
     * https://stackoverflow.com/questions/2418485/how-do-i-convert-a-byte-array-to-base64-in-java
     * @return
     */
    @Override
    public String getByteArray() {
        String encodedByteArray = Base64.getEncoder().encodeToString(byteArray);
        return encodedByteArray;
    }
}
