package com.app.feng.drawmyavatar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import com.app.feng.drawmyavatar.view.AvatarView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    AvatarView avatarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        avatarView = (AvatarView) findViewById(R.id.avatar_view);

        saveAvatar();
    }

    private void saveAvatar() {
        Bitmap avatarIcon = avatarView.getDrawingCache();

        String path = saveAvatarToFile(avatarIcon);

        saveAvatarToSystem(path);

    }

    private void saveAvatarToSystem(String path) {
        //插入系统图库
        try {
            MediaStore.Images.Media.insertImage(getContentResolver(),path,"avatar","头像");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //刷新
        sendBroadcast(
                new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,Uri.fromFile(new File(path))));

    }

    private String saveAvatarToFile(Bitmap avatarIcon) {
        //写入文件
        File avatar_dri = new File(Environment.getExternalStorageDirectory(),"avatar");
        avatar_dri.mkdir();
        File avatar_file = new File(avatar_dri,"avatar.png");

        try {
            FileOutputStream outputStream = new FileOutputStream(avatar_file);
            avatarIcon.compress(Bitmap.CompressFormat.PNG,100,outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return avatar_file.getAbsolutePath();
    }
}
