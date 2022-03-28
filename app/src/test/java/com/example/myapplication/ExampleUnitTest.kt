package com.example.myapplication

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    /*
      /* testImplementation "org.mockito:mockito-core:3.+"
    testImplementation 'org.mockito:mockito-inline:3.3.3'
    testImplementation 'org.robolectric:robolectric:4.7.3'*/
    @Test
    fun onConfirmClicked() {
       *//*val scenario = ActivityScenario.launch(OkHttpTestTwoActivity::class.java)
        scenario.onActivity { activity ->
            val button = activity.findViewById<Button>(R.id.btnSyncGet)
            button.performClick()
        }*//*


        *//*ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
          scenario.onActivity(activity -> {
              Button button = activity.findViewById(R.id.btn_go_to_unit);
              button.performClick();
              //期望的intent
              Intent expectedIntent = new Intent(activity, UnitTestActivity.class);
              //真实的intent
              Intent actual = shadowOf(context)
                  .getNextStartedActivity();
              assertEquals(expectedIntent.getComponent(),actual.getComponent());
          });*//*


    }*/


}

