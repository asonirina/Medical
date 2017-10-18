package com.by.iason.model;

import static multichain.command.GrantCommand.*;
import static multichain.command.GrantCommand.ADMIN;

/**
 * Created by iason
 * on 10/5/2017.
 */
public interface Permissions {
    int[] INIT = {CONNECT, SEND, RECEIVE};
    int[] MINISTRY_ADMIN = {CONNECT, SEND, RECEIVE, ACTIVATE, CREATE, ISSUE, MINE, ADMIN};
    int[] DOCTOR_ADMIN = {CONNECT, SEND, RECEIVE, ACTIVATE, CREATE, MINE};
    int[] DOCTOR = {CONNECT, SEND, RECEIVE};
    int[] PATIENT = {CONNECT, SEND, RECEIVE};
}
