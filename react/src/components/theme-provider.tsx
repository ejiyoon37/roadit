import React, { useEffect, useState } from "react";

// Props 타입 정의
interface ThemeProviderProps {
  children: React.ReactNode;
  defaultTheme: "light" | "dark";
}

export function ThemeProvider({ children, defaultTheme }: ThemeProviderProps) {
  const [theme, setTheme] = useState(defaultTheme);

  // 테마가 변경될 때마다 DOM에 반영
  useEffect(() => {
    const root = window.document.documentElement;
    root.classList.remove("light", "dark");
    root.classList.add(theme);
  }, [theme]);

  // 테마 변경 함수 (예시)
  const toggleTheme = () => {
    setTheme((prevTheme) => (prevTheme === "light" ? "dark" : "light"));
  };

  return (
    <div className={theme}>
      {/* 테마 변경 버튼 (실제 프로젝트에 맞게 조정 가능) */}
      <button onClick={toggleTheme}>테마 변경</button>
      {children}
    </div>
  );
}