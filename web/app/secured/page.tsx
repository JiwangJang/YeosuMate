"use server";

import type { AppProps } from "next/app";
import { redirect } from "next/navigation";

export default function SecuredPage({ Component, pageProps }: AppProps): JSX.Element {
    fetch("/api/session")
        .then((res) => res.json())
        .then((res) => {
            console.log(res);
            // if(false){
            //     redirect("/");
            // }
        });

    return (
        <div>
            <p>로그인해야만 볼 수 있는 페이지</p>
        </div>
    );
}
