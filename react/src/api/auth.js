export async function signup(userData) {
    try {
      const response = await fetch("http://localhost:8080/api/auth/signup", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(userData),
      });
  
      const data = await response.json();
      if (!response.ok) {
        throw new Error(data.error?.message || "회원가입에 실패했습니다.");
      }
      return data;
    } catch (error) {
      throw new Error(error.message);
    }
  }

export async function sendVerificationCode(email) {
  try {
    const response = await fetch(
      `http://localhost:8080/api/auth/send-code?email=${encodeURIComponent(email)}`,
      {
        method: "POST",
      }
    );

    const data = await response.json();
    if (!response.ok) {
      throw new Error(data.error?.message || "인증번호 전송에 실패했습니다.");
    }
    return data;
  } catch (error) {
    throw new Error(error.message);
  }
}

export async function verifyCode(email, code) {
  try {
    const response = await fetch(
      `http://localhost:8080/api/auth/verify-code?email=${encodeURIComponent(email)}&code=${code}`,
      {
        method: "POST",
      }
    );

    const data = await response.json();
    if (!response.ok) {
      throw new Error(data.error?.message || "인증번호 검증에 실패했습니다.");
    }
    return data;
  } catch (error) {
    throw new Error(error.message);
  }
}

export async function resetPassword(data) {
  try {
    const response = await fetch("http://localhost:8080/api/auth/reset-password", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });

    const result = await response.json();
    if (!response.ok) {
      throw new Error(result.error?.message || "비밀번호 초기화에 실패했습니다.");
    }
    return result;
  } catch (error) {
    throw new Error(error.message);
  }
}

export async function changePassword(data) {
  try {
    const response = await fetch("http://localhost:8080/api/auth/change-password", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });

    const result = await response.json();
    if (!response.ok) {
      throw new Error(result.error?.message || "비밀번호 변경에 실패했습니다.");
    }
    return result;
  } catch (error) {
    throw new Error(error.message);
  }
}
export async function login(payload) {
  return axios.post("/api/login", payload);
}