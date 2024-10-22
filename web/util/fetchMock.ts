const mockFetch: () => Promise<void> = async () => {
    await new Promise<void>((resolve, reject) => {
        setTimeout(() => {
            console.log("2초기다림");
            resolve();
        }, 2000);
    });
};

export default mockFetch;
