package erebe.lab4;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;


public class MainActivity extends Activity implements Constants, IUpdatable{

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Update();
        }
    };
    private chronos gameView;
    private long lastTime;
    private long lag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameView = findViewById(R.id.game_view);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        lastTime = System.nanoTime();
        RequestUpdate();
    }

    private void RequestUpdate()
    {
        handler.postDelayed(runnable, UPDATE_TIME_MS);
    }

    @Override
    public void Update() {

        long currentTime = System.nanoTime();
        long elapsedTime = currentTime - lastTime;
        lag += elapsedTime;

        while (lag >= UPDATE_TIME_NS)
        {
            lag -= UPDATE_TIME_NS;
            gameView.Update();
        }

        RequestUpdate();

        lastTime = currentTime;
    }

    private void CancelUpdate()
    {
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onPause()
    {
        CancelUpdate();
        super.onPause();
    }

    public void addSides(View v)
    {
        if (gameView.sides < 7)
        {
            gameView.sides++;
        }
    }

    public void substractSides(View v)
    {
        if (gameView.sides > 3)
        {
            gameView.sides--;
        }
    }
}
