package tests;

import com.android.storyapp.activity.CustomizeActivity;
import com.android.storyapp.model.Answer;
import com.android.storyapp.model.Dialog;

import org.junit.Test;

public class CustomizeTests {

    @Test
    public void testRecursionToSaveDialog() {
        Dialog dialog = new Dialog();
        Answer answer1 = new Answer();
        Answer answer2 = new Answer();

        dialog.answer1 = answer1;
        dialog.answer2 = answer2;

        answer1.previousDialog = dialog;
        answer2.previousDialog = dialog;

        CustomizeActivity
    }
}
