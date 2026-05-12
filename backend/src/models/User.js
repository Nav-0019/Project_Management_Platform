const { DataTypes } = require('sequelize');
const sequelize = require('../config/database');

const User = sequelize.define('User', {
    id: { type: DataTypes.UUID, defaultValue: DataTypes.UUIDV4, primaryKey: true },
    name: { type: DataTypes.STRING, allowNull: false },
    email: { type: DataTypes.STRING, allowNull: false, unique: true },
    password: { type: DataTypes.STRING, allowNull: false },
    role: { type: DataTypes.STRING, defaultValue: 'student' }, // student, faculty, admin
    idNumber: { type: DataTypes.STRING, allowNull: false, unique: true }, // Roll No or Faculty ID
    department: { type: DataTypes.STRING, allowNull: false },
    profile: {
        type: DataTypes.JSON,
        defaultValue: {
            headline: '',
            aboutMe: '',
            skills: [],
            cgpa: '',
            github: '',
            linkedin: '',
            isLookingForTeam: true,
            isOpenToJoin: true
        }
    }
}, {
    timestamps: true
});

module.exports = User;
