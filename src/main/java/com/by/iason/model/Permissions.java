package com.by.iason.model;

import static multichain.command.GrantCommand.CONNECT;
import static multichain.command.GrantCommand.SEND;
import static multichain.command.GrantCommand.RECEIVE;
import static multichain.command.GrantCommand.ACTIVATE;

/**
 * Created by iason
 * on 10/5/2017.
 */
public class Permissions {
    public static final int DOCTOR_ADMIN = CONNECT + SEND + RECEIVE + ACTIVATE;
    public static final int DOCTOR = CONNECT + SEND + RECEIVE;
}
