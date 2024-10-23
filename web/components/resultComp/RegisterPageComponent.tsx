"use client";

import { useRef, useState } from "react";
import DefaultInput from "../defaultComp/DefaultInput";
import DefaultButton from "../defaultComp/DefaultButton";
import ColorSet from "@/util/colorSet";
import axios from "axios";
import { useRouter } from "next/navigation";
import { ProcessResult } from "@/types/ProcessResult";

const RegisterPageComponent = () => {
    const [errorMsg, setErrorMsg] = useState<string>("");

    const idInputRef = useRef<HTMLInputElement>(null);
    const passwordInputRef = useRef<HTMLInputElement>(null);
    const nicknameInputRef = useRef<HTMLInputElement>(null);

    const router = useRouter();

    const onClickEvent: () => Promise<void> = async () => {
        const id = idInputRef.current?.value;
        const password = passwordInputRef.current?.value;
        const nickname = nicknameInputRef.current?.value;

        try {
            const registerResult = await axios.post(
                "http://localhost:8080/auth/register",
                {
                    id,
                    password,
                    nickname,
                },
                {
                    headers: {
                        "Content-Type": "application/json",
                    },
                }
            );

            const resultObj: ProcessResult = registerResult.data;

            if (resultObj.isSuccess) {
                router.push("/secured");
            } else {
                setErrorMsg(resultObj.msg);
            }
        } catch (error) {
            console.error(error);
            setErrorMsg("무언가 에러가 생겼어요!");
        }
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
            <DefaultInput inputRef={passwordInputRef} placeholder="비밀번호를 입력해주세요" title="비밀번호" />
            <DefaultInput inputRef={nicknameInputRef} placeholder="닉네임을 입력해주세요" title="닉네임" />
            <DefaultButton
                buttonText="회원가입"
                bgColor="#212121"
                onClickEvent={onClickEvent}
                fontColor={ColorSet.WHITE}
            />
            {errorMsg && (
                <p
                    style={{
                        marginTop: 12,
                        color: ColorSet.ERROR,
                        fontWeight: 800,
                    }}
                >
                    {errorMsg}
                </p>
            )}
        </div>
    );
};

export default RegisterPageComponent;
