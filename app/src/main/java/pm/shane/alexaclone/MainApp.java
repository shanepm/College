package pm.shane.alexaclone;

import android.app.Application;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.integreight.onesheeld.sdk.OneSheeldDevice;
import com.integreight.onesheeld.sdk.OneSheeldSdk;

import java.util.Locale;

/**
 * Created by Shane on 17/09/2017.
 */

public class MainApp extends Application {

    private static Application mInstance;
    private static OneSheeldDevice device;
    private static TextToSpeech tts;
    private static boolean canSpeak = false;

    @Override
    public void onCreate() {
        mInstance = this;
        tts = new TextToSpeech(getContext(), (int status) -> {
            if(status == TextToSpeech.SUCCESS){
                int result=tts.setLanguage(Locale.US);
                if(result==TextToSpeech.LANG_MISSING_DATA ||
                        result==TextToSpeech.LANG_NOT_SUPPORTED){
                    Log.e("error", "This Language is not supported");
                }
                else{
                    canSpeak = true;
                }
            }
            else
                Log.e("error", "Initilization Failed!");
        });
        OneSheeldSdk.init(getContext());
        super.onCreate();
    }

    public static boolean canSpeak() {
        return canSpeak;
    }

    public static void speak(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public static Application get() {
        return mInstance;
    }

    public static Context getContext() {
        return (Context)mInstance;
    }

    public static OneSheeldDevice getConnectedDevice() {
        return device;
    }

    public static void setConnectedDevice(OneSheeldDevice device) {
        MainApp.device = device;
    }
}
