const mongoose = require('mongoose');

const UserSchema = new mongoose.Schema({
    name: { type: String, required: true },
    email: { type: String, required: true, unique: true },
    password: { type: String, required: true },
    role: { type: String, enum: ['student', 'faculty', 'admin'], default: 'student' },
    idNumber: { type: String, required: true, unique: true }, // Roll No or Faculty ID
    department: { type: String, required: true },
    profile: {
        headline: String,
        aboutMe: String,
        skills: [String],
        cgpa: String,
        github: String,
        linkedin: String,
        isLookingForTeam: { type: Boolean, default: true },
        isOpenToJoin: { type: Boolean, default: true }
    },
    createdAt: { type: Date, default: Date.now }
});

module.exports = mongoose.model('User', UserSchema);
