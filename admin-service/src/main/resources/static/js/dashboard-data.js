document.addEventListener("DOMContentLoaded", function () {
    const data = window.dashboardData;
  
    if (!data) return;
  
    new Chart(document.getElementById("revenueChart"), {
      type: "line",
      data: {
        labels: data.revenueLabels,
        datasets: [{
          label: "Doanh thu (VND)",
          data: data.revenueData,
          borderColor: "rgba(75, 192, 192, 1)",
          fill: false,
          tension: 0.4
        }]
      }
    });
  
    new Chart(document.getElementById("userTypeChart"), {
      type: "pie",
      data: {
        labels: ["Admin", "Customer"],
        datasets: [{
          data: data.userTypes,
          backgroundColor: ["#60A5FA", "#FBBF24"]
        }]
      }
    });
  
    new Chart(document.getElementById("managerChart"), {
      type: "bar",
      data: {
        labels: ["Users", "Customers", "Products"],
        datasets: [{
          label: "Số lượng",
          data: data.managerStats,
          backgroundColor: "#34D399"
        }]
      }
    });
  });
  