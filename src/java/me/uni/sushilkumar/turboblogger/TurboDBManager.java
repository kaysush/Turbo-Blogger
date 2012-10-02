/*
 * Copyright Sushil Kumar <0120sushil@gmail.com>.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 *     http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * under the License.
 */
package me.uni.sushilkumar.turboblogger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sushil Kumar <0120sushil@gmail.com>
 */
public class TurboDBManager {

    private String host;
    private String username;
    private String password;
    private String database;

    public TurboDBManager(String host, String username, String password, String database) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.database = database;
    }

    public boolean isValidDB() {
        boolean isValid = false;
        try {

            String url = "jdbc:mysql://" + this.host + "/" + this.database;
            String dbClass = "com.mysql.jdbc.Driver";
            Class.forName(dbClass);
            Connection con = DriverManager.getConnection(url, username, password);
            isValid=true;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TurboDBManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            isValid=false;
            Logger.getLogger(TurboDBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isValid;
    }

    @Override
    public String toString() {
        return "This is TurboDBManager Object";
    }
}
