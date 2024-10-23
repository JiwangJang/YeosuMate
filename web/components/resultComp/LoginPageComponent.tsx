"use client";

import { useRef } from "react";
import DefaultInput from "../defaultComp/DefaultInput";
import DefaultButton from "../defaultComp/DefaultButton";
import ColorSet from "@/util/colorSet";

const LoginPageComponent = () => {
    const idInputRef = useRef<HTMLInputElement>(null);
    const passwordInputRef = useRef<HTMLInputElement>(null);
    const onClickEvent: () => Promise<void> = async () => {
        const id = idInputRef.current?.value;
        const password = passwordInputRef.current?.value;

        fetch("http://localhost:8080/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                id,
                password,
            }),
        });
    };

    return (
        <div
            style={{
                backgroundColor: ColorSet.WHITE,
                padding: "36px",
                borderColor: ColorSet.BORDERCOLOR,
                borderWidth: "1px",
                borderStyle: "solid",
                borderRadius: "8px",
            }}
            className="center-in-relative"
        >
            <p
                style={{
                    fontSize: 32,
                    fontWeight: 700,
                    marginBottom: 24,
                }}
            >
                로그인 페이지
            </p>

            <DefaultInput inputRef={idInputRef} placeholder="아이디를 입력해주세요" title="아이디" />
            <DefaultInput inputRef={passwordInputRef} placeholder="비밀번호를 입력해주세요" title="비밀번호" />
            <DefaultButton
                buttonText="로그인"
                bgColor="#212121"
                onClickEvent={onClickEvent}
                fontColor={ColorSet.WHITE}
            />
        </div>
    );
};

export default LoginPageComponent;
