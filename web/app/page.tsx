import Link from "next/link";

export default function Home() {
    return (
        <div
            className="center-in-relative"
            style={{
                display: "flex",
                flexDirection: "column",
                gap: 12,
                fontSize: 24,
            }}
        >
            <p>아무튼 홈페이지임</p>
            <Link href={"/register"}>회원가입페이지 ㄱㄱ</Link>
            <Link href={"/login"}>로그인페이지 ㄱㄱ</Link>
        </div>
    );
}
