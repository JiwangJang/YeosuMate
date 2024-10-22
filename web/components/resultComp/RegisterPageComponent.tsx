"use client";

import { useRef } from "react";
import DefaultInput from "../defaultComp/DefaultInput";
import DefaultButton from "../defaultComp/DefaultButton";
import ColorSet from "@/util/colorSet";

const RegisterPageComponent = () => {
    const idInputRef = useRef<HTMLInputElement>(null);
    const pwInputRef = useRef<HTMLInputElement>(null);
    const onClickEvent: () => Promise<void> = async () => {
        const id = idInputRef.current?.value;
        const pw = pwInputRef.current?.value;

        fetch("http://localhost:8080/auth/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                id,
                pw,
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
                회원가입 페이지
            </p>

            <DefaultInput inputRef={idInputRef} placeholder="아이디를 입력해주세요" title="아이디" />
            <DefaultInput inputRef={pwInputRef} placeholder="비밀번호를 입력해주세요" title="비밀번호" />
            <DefaultButton
                buttonText="회원가입"
                bgColor="#212121"
                onClickEvent={onClickEvent}
                fontColor={ColorSet.WHITE}
            />
        </div>
    );
};

export default RegisterPageComponent;
