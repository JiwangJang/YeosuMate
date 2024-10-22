"use client";

import ColorSet from "@/util/colorSet";
import { RefObject, useState } from "react";

const DefaultInput = ({
    inputRef,
    placeholder,
    title,
}: {
    inputRef: RefObject<HTMLInputElement>;
    placeholder: string;
    title: string;
}) => {
    const [isFocus, setIsFocus] = useState(false);

    return (
        <div
            style={{
                marginBottom: 12,
            }}
        >
            <p
                style={{
                    fontSize: 20,
                    marginBottom: 8,
                    paddingLeft: 2,
                    fontWeight: 600,
                }}
            >
                {title}
            </p>
            <input
                style={{
                    padding: 12,
                    fontSize: 18,
                    borderWidth: 1,
                    borderStyle: "solid",
                    borderColor: isFocus ? ColorSet.BLACK : ColorSet.BORDERCOLOR,
                    borderRadius: 4,
                    width: 400,
                }}
                type="text"
                placeholder={placeholder}
                ref={inputRef}
                onFocus={() => setIsFocus(true)}
                onBlur={() => setIsFocus(false)}
            />
        </div>
    );
};

export default DefaultInput;
