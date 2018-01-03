package tw.idv.dindin00.databasetutorialsample;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    //用來顯示結果的ListView宣告
    ListView lv_show;
    //SQLite資料庫變數宣告
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //資料庫初始化
        //第一個參數是命名資料庫名稱(副檔名為.db)
        //第二個參數是設定資料庫存取權限為私有，即只有此APP可以存取
        //第三個參數是針對錯誤情況的處理，若不想自行特別處理就放置null
        database = openOrCreateDatabase("test.db", MODE_PRIVATE, null);
        //利用execSQL執行SQL的DDL及部分DML指令
        //透過if not exists只要已經有該資料表存在就不會重複創建
        database.execSQL("create table if not exists students(id integer primary key, name text not null, class text not null)");
        //插入三筆資料
        //注意SQL語法中如果有要採用字串時必須與最外層Android字串包覆的引號不同
        //如本範例外層android的是雙引號，而內部SQL語法為單引號
        database.execSQL("insert into students values(10510108, '王小明', '資二A')");
        database.execSQL("insert into students values(10510215, '鄭成功', '資二B')");
        database.execSQL("insert into students values(10410345, '陳筱玲', '資三C')");

    }
}
