const { DataTypes } = require('sequelize');
const sequelize = require('../config/database');
const User = require('./User');

const Project = sequelize.define('Project', {
    id: { type: DataTypes.UUID, defaultValue: DataTypes.UUIDV4, primaryKey: true },
    name: { type: DataTypes.STRING, allowNull: false },
    description: { type: DataTypes.TEXT },
    domain: { type: DataTypes.STRING },
    goals: { type: DataTypes.JSON, defaultValue: [] },
    leaderId: { type: DataTypes.UUID, allowNull: false },
    members: { type: DataTypes.JSON, defaultValue: [] }, // array of user IDs or objects
    mentorId: { type: DataTypes.UUID },
    status: { type: DataTypes.STRING, defaultValue: 'pending' }, // pending, active, reviewed, approved, completed
    progress: { type: DataTypes.INTEGER, defaultValue: 0 },
    files: { type: DataTypes.JSON, defaultValue: [] }, // array of file objects
    reviews: { type: DataTypes.JSON, defaultValue: [] } // array of review objects
}, {
    timestamps: true
});

// Associations
Project.belongsTo(User, { as: 'leader', foreignKey: 'leaderId' });
Project.belongsTo(User, { as: 'mentor', foreignKey: 'mentorId' });

module.exports = Project;
