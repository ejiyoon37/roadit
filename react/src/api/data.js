import axios from "axios";

export const fetchCountries = async () => {
  try {
    const response = await axios.get("https://restcountries.com/v3.1/all");
    return response.data
      .map(country => ({
        value: country.cca3,
        label: country.name.common
      }))
      .sort((a, b) => a.label.localeCompare(b.label));
  } catch (error) {
    console.error("Failed to fetch countries:", error);
    return [
      { value: "1", label: "국가 1" },
      { value: "2", label: "국가 2" },
      { value: "3", label: "국가 3" }
    ];
  }
};

export const fetchUniversities = async () => {
  try {
    const response = await axios.get("http://universities.hipolabs.com/search");
    return response.data
      .map((uni, index) => ({
        value: index.toString(),
        label: uni.name
      }))
      .sort((a, b) => a.label.localeCompare(b.label));
  } catch (error) {
    console.error("Failed to fetch universities:", error);
    return [
      { value: "1", label: "학교 1" },
      { value: "2", label: "학교 2" },
      { value: "3", label: "학교 3" }
    ];
  }
};

export const fetchOccupations = async () => {
  try {
    const response = await axios.get("https://jsonplaceholder.typicode.com/users");
    return response.data
      .map((user, index) => ({
        value: index.toString(),
        label: user.name // 사용자 이름을 직업 이름으로 가정
      }))
      .sort((a, b) => a.label.localeCompare(b.label));
  } catch (error) {
    console.error("Failed to fetch occupations:", error);
    return [
      { value: "1", label: "직업 1" },
      { value: "2", label: "직업 2" },
      { value: "3", label: "직업 3" }
    ];
  }
};