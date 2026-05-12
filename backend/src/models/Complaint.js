const { DataTypes } = require('sequelize');
const sequelize = require('../config/database');
const User = require('./User');

const Complaint = sequelize.define('Complaint', {
    id: { type: DataTypes.UUID, defaultValue: DataTypes.UUIDV4, primaryKey: true },
    userId: { type: DataTypes.UUID, allowNull: false },
    subject: { type: DataTypes.STRING, allowNull: false },
    message: { type: DataTypes.TEXT, allowNull: false },
    status: { type: DataTypes.STRING, defaultValue: 'open' }
}, {
    timestamps: true
});

Complaint.belongsTo(User, { as: 'user', foreignKey: 'userId' });

module.exports = Complaint;
