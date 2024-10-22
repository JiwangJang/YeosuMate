import "../public/css/reset.css";
import "../public/css/fonts.css";
import "../public/css/styles.css";
import ColorSet from "@/util/colorSet";

export default function RootLayout({
    children,
}: Readonly<{
    children: React.ReactNode;
}>) {
    return (
        <html lang="ko">
            <body>
                <div
                    className="container"
                    style={{
                        backgroundColor: ColorSet.BACKGROUND,
                    }}
                >
                    {children}
                </div>
            </body>
        </html>
    );
}
