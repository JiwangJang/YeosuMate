package com.practice.entities;

public enum RoleEnum {
    USER {
        @Override
        public String toString() {
            return "USER";
        }
    },
    ADMIN {
        @Override
        public String toString() {
            return "ADMIN";
        }
    },
    SUPER_ADMIN {
        @Override
        public String toString() {
            return "SUPER_ADMIN";
        }
    }
}
