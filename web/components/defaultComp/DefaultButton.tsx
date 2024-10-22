"use client";

import { useState } from "react";

const DefaultButton = ({
    buttonText,
    bgColor,
    fontColor = "#212121",
    onClickEvent,
}: {
    buttonText: string;
    bgColor: string;
    fontColor?: string;
    onClickEvent: () => Promise<void>;
}) => {
    const [isProgress, setIsProgress] = useState<boolean>(false);

    return (
        <button
            style={{
                backgroundColor: bgColor,
                opacity: isProgress ? 0.5 : 1,
                color: fontColor,
                fontSize: 18,
                width: "100%",
                padding: "8px 0px",
                borderRadius: 4,
                cursor: "pointer",
            }}
            onClick={() => {
                setIsProgress(true);
                const onClickEventResult = onClickEvent();

                if (onClickEventResult instanceof Promise) {
                    onClickEventResult.then(() => setIsProgress(false));
                    return;
                }

                setIsProgress(false);
            }}
        >
            {isProgress ? "처리중.." : buttonText}
        </button>
    );
};

export default DefaultButton;
