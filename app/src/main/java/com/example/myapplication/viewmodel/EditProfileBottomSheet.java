package com.example.myapplication.viewmodel;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.activity.SettingsActivity;
import com.example.myapplication.databinding.EditProfileBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import api.ApiService;
import api.RetrofitClient;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileBottomSheet extends BottomSheetDialogFragment {
    public static final String SHARED_PREFS = "UserPrefs";
    private static final int REQUEST_CODE_PICK_IMAGE = 100;
    private static final int REQUEST_CODE_TAKE_PHOTO = 101;

    private EditProfileBinding binding;  // ViewBinding instance
    private Uri selectedImageUri;
    private Bitmap capturedPhoto;

    public static EditProfileBottomSheet newInstance(String userID, String firstName, String middleName, String lastName,
                                                     String profilePictureFileName, String specialty, String email,
                                                     String licenseNo, String professionalType, String Address,
                                                     String personnel_id, String position_id) {
        EditProfileBottomSheet fragment = new EditProfileBottomSheet();
        Bundle args = new Bundle();
        args.putString("userID", userID);

        args.putString("firstName", firstName);
        args.putString("middleName", middleName);
        args.putString("lastName", lastName);
        args.putString("profilePicture", profilePictureFileName);  // Store with this key
        args.putString("specialty", specialty);
        args.putString("email", email);
        args.putString("licenseNo", licenseNo);
        args.putString("professionalType", professionalType);
        args.putString("address", Address);
        args.putString("personnel_id", personnel_id);
        args.putString("position_id", position_id);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Initialize ViewBinding
        binding = EditProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Get data from arguments
        if (getArguments() != null) {
            binding.firstNameEditText.setText(getArguments().getString("firstName"));
            binding.middlenameText.setText(getArguments().getString("middleName"));
            binding.lastNameEditText.setText(getArguments().getString("lastName"));
            binding.emailText.setText(getArguments().getString("email"));
            binding.specialty.setText(getArguments().getString("specialty"));
            binding.profType.setText(getArguments().getString("professionalType"));
            binding.licenseNo.setText(getArguments().getString("licenseNo"));
            binding.Address.setText(getArguments().getString("address"));
            binding.positionidEditText.setText(getArguments().getString("position_id"));
            binding.personneliidEditText.setText(getArguments().getString("personnel_id"));

            String profilePictureFileName = getArguments().getString("profilePicture");  // Retrieve the correct key

            // If there's a profile picture URL, load it into the ImageView
            if (profilePictureFileName != null && !profilePictureFileName.isEmpty()) {
                String profilePictureUrl = "http://192.168.1.2/lutayanrhu/user_images/" + profilePictureFileName;  // Construct the full URL
                Glide.with(this)
                        .load(profilePictureUrl)
                        .apply(new RequestOptions()
                                .override(120, 120)  // Match ImageView dimensions
                                .circleCrop()       // Ensure circular crop
                        )
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))  // Optional: rounded corners
                        .error(R.drawable.doctor)  // Default image if loading fails
                        .into(binding.doctorProfileImage);  // Set the image to ImageView
            }
        }


        // Profile Image Click Listener
        binding.doctorProfileImage.setOnClickListener(v -> showImageSourceOptions());

        // Submit Button Click Listener
        binding.updateProfile.setOnClickListener(v -> {
            String firstName = binding.firstNameEditText.getText().toString().trim();
            String middleName = binding.middlenameText.getText().toString().trim();
            String lastName = binding.lastNameEditText.getText().toString().trim();
            String email = binding.emailText.getText().toString().trim();
            String specialtyText = binding.specialty.getText().toString().trim();
            String profTypeText = binding.profType.getText().toString().trim();
            String licenseNoText = binding.licenseNo.getText().toString().trim();
            String AddressText = binding.Address.getText().toString().trim();
            String Personnel = binding.personneliidEditText.getText().toString().trim();
            String Position = binding.positionidEditText.getText().toString().trim();

            // Check for required fields
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() ||
                    specialtyText.isEmpty() || profTypeText.isEmpty() || licenseNoText.isEmpty()) {
                Toast.makeText(getContext(), "All fields are required.", Toast.LENGTH_SHORT).show();
            } else {
                String userID = getArguments() != null ? getArguments().getString("userID") : "";
                if (userID.isEmpty()) {
                    Toast.makeText(getContext(), "User ID is missing.", Toast.LENGTH_SHORT).show();
                    Log.d("User ID", "User ID: " + userID);
                    return;
                }
                // Call API to update profile
                updateProfile(userID, firstName, middleName, lastName, email, specialtyText, profTypeText, licenseNoText, AddressText, Personnel, Position);
            }
        });

        return view;
    }

    private void showImageSourceOptions() {
        CharSequence[] options = {"Choose from Gallery", "Take a Photo"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Profile Image Source");
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                openImagePicker();
            } else if (which == 1) {
                openCamera();
            }
        });
        builder.show();
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_PICK_IMAGE && data != null && data.getData() != null) {
                selectedImageUri = data.getData();
                binding.doctorProfileImage.setImageURI(selectedImageUri);
            } else if (requestCode == REQUEST_CODE_TAKE_PHOTO && data != null && data.getExtras() != null) {
                capturedPhoto = (Bitmap) data.getExtras().get("data");
                // Convert Bitmap to File and upload
                File photoFile = saveBitmapToFile(capturedPhoto);
                if (photoFile != null) {
                    selectedImageUri = Uri.fromFile(photoFile);  // Set it as selected URI
                    binding.doctorProfileImage.setImageBitmap(capturedPhoto);
                }
            }
        }
    }

    // Method to convert Bitmap to File
    private File saveBitmapToFile(Bitmap bitmap) {
        File file = new File(getActivity().getCacheDir(), "profile_pic_" + System.currentTimeMillis() + ".jpg");
        try (FileOutputStream outStream = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream); // Compress the image
            outStream.flush();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void updateProfile(String userID, String firstName, String middleName,
                               String lastName, String email, String specialtyText,
                               String profTypeText, String licenseNoText, String Address, String personnelid, String positionid) {
        MultipartBody.Part profilePicturePart = null;

        File file = null;
        if (selectedImageUri != null) {
            file = getFileFromUri(selectedImageUri);
            if (file != null) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                profilePicturePart = MultipartBody.Part.createFormData("profile_picture", file.getName(), requestBody);
            }
        }



        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        RequestBody userIDPart = RequestBody.create(MediaType.parse("text/plain"), userID);
        RequestBody firstNamePart = RequestBody.create(MediaType.parse("text/plain"), firstName);
        RequestBody middleNamePart = RequestBody.create(MediaType.parse("text/plain"), middleName);
        RequestBody lastNamePart = RequestBody.create(MediaType.parse("text/plain"), lastName);
        RequestBody emailPart = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody specialtyPart = RequestBody.create(MediaType.parse("text/plain"), specialtyText);
        RequestBody profTypePart = RequestBody.create(MediaType.parse("text/plain"), profTypeText);
        RequestBody licenseNoPart = RequestBody.create(MediaType.parse("text/plain"), licenseNoText);
        RequestBody address = RequestBody.create(MediaType.parse("text/plain"), Address);
        RequestBody personnelPart = RequestBody.create(MediaType.parse("text/plain"), personnelid);
        RequestBody positionPart = RequestBody.create(MediaType.parse("text/plain"), positionid);

        Call<UpdateprofileResponse> call = apiService.updateProfile(
                userIDPart,
                firstNamePart,
                middleNamePart,
                lastNamePart,
                emailPart,
                specialtyPart,
                profTypePart,
                licenseNoPart,
                address,
                personnelPart,
                positionPart,
                profilePicturePart
        );

        MultipartBody.Part finalProfilePicturePart = profilePicturePart;
        File finalFile = file;
        MultipartBody.Part finalProfilePicturePart1 = profilePicturePart;
        File finalFile1 = file;
        call.enqueue(new Callback<UpdateprofileResponse>() {
            @Override
            public void onResponse(Call<UpdateprofileResponse> call, Response<UpdateprofileResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Profile updated successfully!", Toast.LENGTH_SHORT).show();


                    // Clear Glide cache
                    Glide.get(getContext()).clearMemory();
                    new Thread(() -> Glide.get(getContext()).clearDiskCache()).start();

                    Context context = requireContext(); // Throws if the fragment is not attached
                    SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    // Save basic profile info
                    editor.putString("first_name", firstName);
                    editor.putString("middlename", middleName);
                    editor.putString("lastname", lastName);
                    editor.putString("email", email);
                    editor.putString("Specialty", specialtyText);
                    editor.putString("ProfessionalType", profTypeText);
                    editor.putString("LicenseNo", licenseNoText);
                    editor.putString("address", Address);

                    // Handle profile picture URL
                    if (response.body() != null && response.body().getData() != null) {
                        String profilePictureUrl = response.body().getData().getProfilePictureUrl();
                        editor.putString("profile_picture", profilePictureUrl); // Save the full profile picture URL
                        Log.d("Profile", "Profile Picture URL: " + profilePictureUrl);

                    } else {
                        Log.e("API", "Response or data is null");
                    }
                    editor.apply();
                    if (getActivity() != null) {
                        ((SettingsActivity) getActivity()).refreshProfile();
                    }

                    dismiss();
                } else {
                    Toast.makeText(getContext(), "Failed to update profile.", Toast.LENGTH_SHORT).show();
                }
            }



            @Override
            public void onFailure(Call<UpdateprofileResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private File getFileFromUri(Uri uri) {
        try {
            InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
            File tempFile = File.createTempFile("profile_pic", ".jpg", getActivity().getCacheDir());
            FileOutputStream outputStream = new FileOutputStream(tempFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            inputStream.close();
            outputStream.close();

            return tempFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
