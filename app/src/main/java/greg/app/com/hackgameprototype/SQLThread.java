package greg.app.com.hackgameprototype;

/**
 * Created by Alex on 4/22/2015.
 */
public class SQLThread implements Runnable
{

    MySQLAccess db;

    volatile int temp;

    @Override
    public void run()
    {
        db = new MySQLAccess();

        temp = db.getTeamCash(1);

    }

    public int getTeamCash()
    {
        return temp;
    }
}
