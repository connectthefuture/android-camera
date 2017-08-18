package xyz.pinaki.android.camera;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;

/**
 * Created by pinaki on 8/11/17.
 */

class Camera2 extends BaseCamera {
    @Override
    public boolean start() {
        return false;
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isCameraOpened() {
        return false;
    }

    @Override
    public void setFacing(int facing) {

    }

    @Override
    public int getFacing() {
        return 0;
    }

    @Override
    public void takePicture(PhotoTakenCallback photoTakenCallback) {

    }

    private void openCamera(int lensFacing, Context context) {
        // TODO: fix this
        CameraManager manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {
            for (String cameraId : manager.getCameraIdList()) {
                CameraCharacteristics characteristics
                        = manager.getCameraCharacteristics(cameraId);

                // We don't use a front facing camera in this sample.
                // this needs to change.
                Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);
                if (facing == null || facing != lensFacing) {
                    continue;
                }
//                TODO: fix this
//                manager.openCamera(cameraId, mStateCallback, cameraHandler);
                return;
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
}
