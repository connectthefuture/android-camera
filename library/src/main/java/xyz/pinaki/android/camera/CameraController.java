package xyz.pinaki.android.camera;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by pinaki on 3/6/17.
 */
// singleton manager class
public class CameraController {
    private static String TAG = CameraController.class.getSimpleName();
    private static class SingletonHolder {
        private static final CameraController INSTANCE = new CameraController();
    }
    private CameraController() {
    }

    public static CameraController getInstance() {
        return SingletonHolder.INSTANCE;
    }
    public void launch(AppCompatActivity activity, int containerID) {
        if (isCamera2Supported(activity)) {
            Log.i(TAG, "Camera2 Supported");
        } else {
            Log.i(TAG, "Camera2 NOT Supported");
        }
        activity.getSupportFragmentManager().beginTransaction().replace(
                containerID, CameraFragment.newInstance(), "CameraFragment").commit();
    }


    private static boolean isCamera2Supported(Context context) {
        if ( Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP ) {
            return false;
        }
        CameraManager cameraManager = (CameraManager)context.getSystemService(Context.CAMERA_SERVICE);
        try {
            if (cameraManager.getCameraIdList().length == 0) {
                return false;
            }
            for (String cameraIdStr : cameraManager.getCameraIdList()) {
                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraIdStr);
                Integer hardwareSupport = cameraCharacteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
                if ( hardwareSupport == null ) {
                    return false;
                }
                if (hardwareSupport == CameraMetadata.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY || hardwareSupport ==
                        CameraMetadata.INFO_SUPPORTED_HARDWARE_LEVEL_LIMITED) {
                    return false;
                }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        return true;
    }
}