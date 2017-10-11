package com.by.iason.model;

import static multichain.command.GrantCommand.*;
import static multichain.command.GrantCommand.ADMIN;

/**
 * Created by iason
 * on 10/5/2017.
 */
public class Permissions {
    public static final int[] MINISTRY_ADMIN = {CONNECT, SEND, RECEIVE, ACTIVATE, CREATE, ISSUE, MINE, ADMIN, WALLET, WALLET_ISSUE};
    public static final int[] DOCTOR_ADMIN = {CONNECT, SEND, RECEIVE, ACTIVATE, CREATE};
    public static final int[] DOCTOR = {CONNECT, SEND, RECEIVE};
    public static final int[] PATIENT = {CONNECT, SEND, RECEIVE};
}
