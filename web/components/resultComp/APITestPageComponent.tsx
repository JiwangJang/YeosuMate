"use client";

const APITestPageComponent = () => {
    // const [result, setResult] = useState<string>("");

    const ApiTest = () => {
        fetch("http://localhost:8080/user/all")
            .then((res) => res.json())
            .then((res) => {
                console.log(res);
            });
    };

    return (
        <div>
            <div>API테스트 페이지임</div>
            <button onClick={ApiTest}>Test Button</button>
            {/* <p>{result}</p> */}
        </div>
    );
};

export default APITestPageComponent;
