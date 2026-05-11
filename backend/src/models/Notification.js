const mongoose = require('mongoose');

const NotificationSchema = new mongoose.Schema({
    title: { type: String, required: true },
    message: { type: String, required: true },
    type: { type: String, enum: ['review', 'deadline', 'team', 'guide', 'announcement', 'admin'], default: 'announcement' },
    targetRole: { type: String, enum: ['all', 'student', 'faculty', 'admin'], default: 'all' },
    targetUser: { type: mongoose.Schema.Types.ObjectId, ref: 'User' }, // Optional: specific user
    targetTeam: { type: mongoose.Schema.Types.ObjectId, ref: 'Project' }, // Optional: specific team
    isRead: { type: Boolean, default: false },
    createdAt: { type: Date, default: Date.now }
});

module.exports = mongoose.model('Notification', NotificationSchema);
