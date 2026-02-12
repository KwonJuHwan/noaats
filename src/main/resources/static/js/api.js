/**
 * 공통 API 호출 함수 (Fetch Wrapper)
 * - 자동으로 JSON 변환
 * - 에러 발생 시 공통 모달 호출
 */
async function callApi(url, method = 'GET', data = null) {
  const options = {
    method: method,
    headers: {
      'Content-Type': 'application/json',
    },
  };

  if (data) {
    options.body = JSON.stringify(data);
  }

  try {
    const response = await fetch(url, options);

    // 1. 응답 실패 (4xx, 5xx)
    if (!response.ok) {
      let errorData;
      try {
        errorData = await response.json();
      } catch (e) {
        // JSON 파싱 실패 시 일반 텍스트 에러로 처리
        throw new Error(`HTTP Error ${response.status}: ${response.statusText}`);
      }
      showErrorModal(errorData); // 모달 띄우기
      throw new Error(errorData.message || "서버 통신 오류"); // 로직 중단용 throw
    }

    // 2. 응답 성공 (200 OK 등) but 내용 없는 경우 처리 (204 No Content 등)
    const text = await response.text();
    return text ? JSON.parse(text) : {};

  } catch (error) {
    // 네트워크 오류 등 fetch 자체가 실패했을 때
    // 이미 모달을 띄운 에러(response.ok 체크)가 아니라면 여기서 처리
    if (!document.getElementById('globalErrorModal').classList.contains('show')) {
      console.error("Network/System Error:", error);
      showErrorModal({
        message: "서버와 통신할 수 없습니다.\n잠시 후 다시 시도해주세요.",
        code: "NETWORK_ERROR"
      });
    }
    throw error;
  }
}

// 에러 모달 띄우기
function showErrorModal(errorResponse) {
  const modalEl = document.getElementById('globalErrorModal');
  const msgEl = document.getElementById('globalErrorMessage');
  const codeEl = document.getElementById('globalErrorCode');

  msgEl.innerText = errorResponse.message || "알 수 없는 오류가 발생했습니다.";
  codeEl.innerText = errorResponse.code ? `Code: ${errorResponse.code}` : '';

  const modal = new bootstrap.Modal(modalEl);
  modal.show();
}