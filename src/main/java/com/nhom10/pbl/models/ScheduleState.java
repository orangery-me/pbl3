package com.nhom10.pbl.models;

public class ScheduleState {
    public static final int WAITING = 0;
    public static final int ACCEPTED = 1;
    public static final int CANCELED = 2;
    public static final int DONE = 3;
    public static final int MISSED = 4;
    public static final int REJECTED = 5;

    public static String getState(int state) {
        switch (state) {
            case WAITING:
                return "Chờ xác nhận";
            case ACCEPTED:
                return "Đã xác nhận";
            case CANCELED:
                return "Đã hủy";
            case DONE:
                return "Đã hoàn thành";
            case MISSED:
                return "Đã bỏ lỡ";
            case REJECTED:
                return "Đã từ chối";
            default:
                return "Không xác định";
        }
    }
}
