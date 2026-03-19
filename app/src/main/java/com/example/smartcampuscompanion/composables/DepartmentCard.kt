package com.example.smartcampuscompanion.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcampuscompanion.data.Department
import com.example.smartcampuscompanion.ui.theme.GreenPrimary

@Composable
fun DepartmentCard(department: Department) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = department.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333) // Changed from BluePrimary to Dark Gray (Requested)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Location: ${department.building}",
                fontSize = 14.sp,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Contact: ${department.contactNumber}",
                fontSize = 14.sp,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Email: ${department.email}",
                fontSize = 14.sp,
                color = GreenPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = department.description,
                fontSize = 13.sp,
                color = Color.Gray,
                lineHeight = 18.sp
            )
        }
    }
}