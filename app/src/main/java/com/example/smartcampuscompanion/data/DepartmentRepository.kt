package com.example.smartcampuscompanion.data

object DepartmentRepository {

    fun getDepartments(): List<Department> {
        return listOf(
            Department(
                name = "Computer Science Department",
                building = "Engineering Building - Room 301",
                contactNumber = "+63 917 123 4567",
                email = "cs@campus.edu.ph",
                description = "Offering programs in Computer Science, Information Technology, and related fields. Home to state-of-the-art computer laboratories."
            ),
            Department(
                name = "College of Engineering",
                building = "Engineering Building - 1st Floor",
                contactNumber = "+63 917 234 5678",
                email = "engineering@campus.edu.ph",
                description = "Providing quality engineering education across various disciplines including Civil, Electrical, and Mechanical Engineering."
            ),
            Department(
                name = "Business Administration",
                building = "Business Hall - Room 205",
                contactNumber = "+63 917 345 6789",
                email = "business@campus.edu.ph",
                description = "Preparing future business leaders through comprehensive programs in Management, Marketing, and Entrepreneurship."
            ),
            Department(
                name = "College of Arts & Sciences",
                building = "Main Academic Building - 2nd Floor",
                contactNumber = "+63 917 456 7890",
                email = "arts.sciences@campus.edu.ph",
                description = "Fostering critical thinking and creativity through liberal arts education, sciences, and humanities programs."
            ),
            Department(
                name = "Nursing Department",
                building = "Medical Sciences Building - 3rd Floor",
                contactNumber = "+63 917 567 8901",
                email = "nursing@campus.edu.ph",
                description = "Training compassionate and competent nurses through rigorous academic and clinical programs."
            ),
            Department(
                name = "Education Department",
                building = "Education Building - Room 101",
                contactNumber = "+63 917 678 9012",
                email = "education@campus.edu.ph",
                description = "Developing future educators through innovative teaching methodologies and practical teaching experiences."
            ),
            Department(
                name = "Architecture Department",
                building = "Design Center - 4th Floor",
                contactNumber = "+63 917 789 0123",
                email = "architecture@campus.edu.ph",
                description = "Cultivating creative and sustainable architectural design through hands-on projects and studio work."
            ),
            Department(
                name = "Student Affairs Office",
                building = "Administration Building - Ground Floor",
                contactNumber = "+63 917 890 1234",
                email = "studentaffairs@campus.edu.ph",
                description = "Supporting student welfare, organizations, and campus life activities. Your go-to for student concerns."
            ),
            Department(
                name = "Library Services",
                building = "University Library - Main Entrance",
                contactNumber = "+63 917 901 2345",
                email = "library@campus.edu.ph",
                description = "Providing comprehensive learning resources, study spaces, and research assistance to the campus community."
            ),
            Department(
                name = "Registrar's Office",
                building = "Administration Building - 2nd Floor",
                contactNumber = "+63 917 012 3456",
                email = "registrar@campus.edu.ph",
                description = "Managing student records, enrollment, grades, and academic credentials. Open Monday to Friday, 8AM-5PM."
            )
        )
    }
}