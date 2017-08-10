package com.lzl.socketdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.lang.StringBuilder
import java.net.Socket

class MainActivity : AppCompatActivity(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        send_button.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.send_button->{
                Thread{
                    val ip = ip_add.text.toString()
                    val data = data_add.text.toString()
                    val socket = Socket(ip,10086)
                    val write = PrintWriter(OutputStreamWriter(socket.getOutputStream()))
                    write.write(data)
                    write.flush()
                    socket.shutdownOutput()
                    val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
                    var line:String? = null
                    val builder = StringBuilder()
                    while (true){
                        line=reader.readLine()
                        if(line==null)
                            break
                        builder.append(line)
                    }
                    reader.close()
                    write.close()
                    socket.close()
                    runOnUiThread{
                        server_data_text.text = builder.toString()
                    }
                }.start()
            }
        }
    }
}
