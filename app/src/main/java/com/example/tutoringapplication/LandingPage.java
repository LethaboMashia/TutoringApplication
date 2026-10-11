package com.example.tutoringapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class LandingPage extends AppCompatActivity {

    public static final String EXTRA_NAME = "extra_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        String name = getIntent().getStringExtra(EXTRA_NAME);
        TextView greeting = findViewById(R.id.greetingTV);
        greeting.setText(getString(R.string.greeting, name == null ? "" : name));

        // The tutorial videos are not stored in the repository, so they are looked up by
        // name at runtime. If a file is missing the app shows a note instead of crashing.
        boolean allFound = true;
        allFound &= setupVideo(R.id.video1, "tutorial1");
        allFound &= setupVideo(R.id.video2, "tutorial2");
        allFound &= setupVideo(R.id.video3, "tutorial3");
        findViewById(R.id.noVideosTV).setVisibility(allFound ? View.GONE : View.VISIBLE);

        findViewById(R.id.logoutBtn).setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginPage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    /** @return true if the video resource exists and was attached, false if it is missing */
    private boolean setupVideo(int viewId, String rawName) {
        VideoView videoView = findViewById(viewId);
        int rawId = getResources().getIdentifier(rawName, "raw", getPackageName());
        if (rawId == 0) {
            videoView.setVisibility(View.GONE);
            return false;
        }
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + rawId);
        MediaController controller = new MediaController(this);
        controller.setAnchorView(videoView);
        videoView.setMediaController(controller);
        videoView.setVideoURI(uri);
        return true;
    }
}
