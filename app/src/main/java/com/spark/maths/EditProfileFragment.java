package com.spark.maths;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class EditProfileFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText nameEditText, usernameEditText;
    private RadioGroup genderRadioGroup;
    private ImageView profileImageView;
    private Button saveButton;

    private DatabaseHelper dbHelper;
    private String selectedImagePath = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        // Initialize views
        nameEditText = view.findViewById(R.id.editTextName);
        usernameEditText = view.findViewById(R.id.editTextUsername);
        genderRadioGroup = view.findViewById(R.id.radioGroupGender);
        profileImageView = view.findViewById(R.id.imageViewProfile);
        saveButton = view.findViewById(R.id.buttonSave);

        dbHelper = new DatabaseHelper(getContext());

        // Load existing data
        loadProfileData();

        profileImageView.setOnClickListener(v -> openImageChooser());

        saveButton.setOnClickListener(v -> saveProfileData());

        return view;
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                selectedImagePath = getRealPathFromURI(selectedImageUri);
                profileImageView.setImageURI(selectedImageUri);
            }
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = requireActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        return "";
    }

    private void loadProfileData() {
        Cursor cursor = dbHelper.getUser();
        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String gender = cursor.getString(cursor.getColumnIndexOrThrow("gender"));
            selectedImagePath = cursor.getString(cursor.getColumnIndexOrThrow("image_path"));

            nameEditText.setText(name);
            usernameEditText.setText(username);

            if (gender.equalsIgnoreCase("Male")) {
                genderRadioGroup.check(R.id.radioMale);
            } else if (gender.equalsIgnoreCase("Female")) {
                genderRadioGroup.check(R.id.radioFemale);
            }

            if (!selectedImagePath.isEmpty()) {
                profileImageView.setImageURI(Uri.parse(selectedImagePath));
            }

            cursor.close();
        }
    }

    private void saveProfileData() {
        String name = nameEditText.getText().toString().trim();
        String username = usernameEditText.getText().toString().trim();
        int selectedGenderId = genderRadioGroup.getCheckedRadioButtonId();
        String gender = "";

        if (selectedGenderId != -1) {
            RadioButton selectedGender = getView().findViewById(selectedGenderId);
            gender = selectedGender.getText().toString();
        }

        if (name.isEmpty() || username.isEmpty() || gender.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean updated = dbHelper.updateUser(name, username, gender, selectedImagePath);
        if (updated) {
            Toast.makeText(getContext(), "Profile updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Failed to update profile", Toast.LENGTH_SHORT).show();
        }
    }
}
